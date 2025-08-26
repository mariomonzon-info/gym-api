# gym-api

API REST para gestionar **clases, horarios y reservas** de un gimnasio.
**Kotlin + Spring Boot 3, PostgreSQL, Flyway, JPA/Hibernate, Testcontainers y Docker.**
Arquitectura **Hexagonal (Ports & Adapters)** organizada por *features*.

---

## Stack

* **Java 21**, **Kotlin 2.x**, **Spring Boot 3.5.x**
* **PostgreSQL 16**, **Flyway** (migraciones)
* **Spring Data JPA**, **HikariCP**
* **Actuator** (health), **springdoc-openapi** (Swagger UI)
* **Testcontainers** para tests de integración

---

## Estructura (simplificada)

```
src/main/kotlin/com/mariomonzon/gymapi
├─ common/                # config y manejo de errores
└─ features/
   ├─ clases/
   │  ├─ domain/         # modelo + puertos
   │  ├─ application/    # casos de uso
   │  └─ infrastructure/
   │     ├─ persistence/ # JPA + adapter
   │     └─ web/         # controllers + DTOs
   └─ (… otras features: horarios, reservas)
src/main/resources/
  ├─ application.yml
  └─ db/migration/       # V1__init_core.sql, V2__seed_clases.sql, …
```

---

## Requisitos

* **Docker** y **Docker Compose**
* **JDK 21** (para ejecutar la API con Gradle)
* **Make/cURL** opcional

---

## 1) Base de datos: PostgreSQL en Docker

Crea `docker-compose.yml` en la raíz del repo:

```yaml
services:
  db:
    image: postgres:16
    container_name: gym-postgres
    environment:
      POSTGRES_USER: mario
      POSTGRES_PASSWORD: Sql1234
      POSTGRES_DB: gym_app
      POSTGRES_INITDB_ARGS: "--locale-provider=icu --icu-locale=es-ES"
    ports:
      - "5432:5432"     # cambia a "5433:5432" si el 5432 está ocupado
    healthcheck:
      test: ["CMD-SHELL","pg_isready -U mario -d gym_app"]
      interval: 5s
      timeout: 3s
      retries: 20
    volumes:
      - pgdata:/var/lib/postgresql/data
volumes:
  pgdata:
```

Levanta la BD:

```bash
docker compose up -d
docker compose logs -f db
```

Verifica conexión:

```bash
docker exec -it gym-postgres psql -U mario -d gym_app -c "select now();"
```

> Seguridad recomendada (mínimo privilegio). Crea un usuario solo para la app y úsalo en `application.yml`:
>
> ```sql
> -- dentro del contenedor (psql)
> CREATE ROLE gym_api WITH LOGIN PASSWORD 'ApP$tr0ng' NOSUPERUSER NOCREATEDB NOCREATEROLE NOINHERIT;
> GRANT USAGE ON SCHEMA core TO gym_api;
> GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA core TO gym_api;
> GRANT USAGE, SELECT, UPDATE ON ALL SEQUENCES IN SCHEMA core TO gym_api;
> ALTER DEFAULT PRIVILEGES IN SCHEMA core
>   GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO gym_api;
> ALTER DEFAULT PRIVILEGES IN SCHEMA core
>   GRANT USAGE, SELECT, UPDATE ON SEQUENCES TO gym_api;
> ```

---

## 2) Configuración de la aplicación

`src/main/resources/application.yml` (ya incluido en el proyecto):

```yaml
spring:
  application.name: gym-api
  datasource:
    url: ${DB_URL:jdbc:postgresql://localhost:5432/gym_app}
    username: ${DB_USER:gym_api}     # o 'mario' si no creaste el rol limitado
    password: ${DB_PASS:ApP$tr0ng}   # o 'Sql1234'
  jpa:
    hibernate:
      ddl-auto: validate
    properties:
      hibernate.default_schema: core
      hibernate.jdbc.time_zone: UTC
      hibernate.format_sql: true
    open-in-view: false
  flyway:
    enabled: true
    schemas: core
    default-schema: core

management.endpoints.web.exposure.include: health,info
server.port: 8080
```

> Las migraciones están en `src/main/resources/db/migration`.
> `V1__init_core.sql` crea el esquema **core** y todas las tablas/índices; `V2__seed_clases.sql` precarga clases demo.

---

## 3) Ejecutar la API

```bash
# desde la raíz del repo
./gradlew bootRun
```

Deberías ver en consola:

* Flyway aplicando migraciones (`Migrating schema "core" to version "1 - init_core"`)
* `Tomcat started on port(s): 8080`
* `Started GymapiApplicationKt …`

Verifica:

```bash
# Health
curl -i http://localhost:8080/actuator/health

# Endpoints de ejemplo
curl -s http://localhost:8080/api/roles | jq
curl -s http://localhost:8080/api/clases | jq
```

---

## 4) Swagger / OpenAPI

* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* OpenAPI JSON: `http://localhost:8080/v3/api-docs`

> En producción se recomienda desactivar Swagger (perfil `prod`).

---

## 5) Tests

Requieren Docker (usa Testcontainers con PostgreSQL):

```bash
./gradlew test
```

---

## 6) Scripts útiles

Cambiar el puerto si está ocupado:

```bash
# Linux/macOS: ver procesos en 8080
lsof -iTCP:8080 -sTCP:LISTEN -n -P
# matar el proceso
kill <PID>
```

Reset rápido de base de datos (solo DEV):

```bash
docker compose down -v && docker compose up -d
```

---

## 7) Roadmap (breve)

* [x] Clases (listar/crear)
* [ ] Horarios (listar por rango, asignar entrenador)
* [ ] Reservas (crear/cancelar, control de aforo)
* [ ] Seguridad JWT + roles
* [ ] Observabilidad (logs JSON, métricas negocio)

---

## Licencia

MIT (o la que elijas).
