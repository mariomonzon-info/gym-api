-- Esquema
CREATE SCHEMA IF NOT EXISTS core;

-- ======= Catálogo =======
CREATE TABLE IF NOT EXISTS core.roles (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);
INSERT INTO core.roles (nombre) VALUES ('cliente'), ('entrenador'), ('admin')
ON CONFLICT (nombre) DO NOTHING;

CREATE TABLE IF NOT EXISTS core.metodos_pago (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL
);
INSERT INTO core.metodos_pago (nombre) VALUES ('tarjeta'), ('efectivo'), ('transferencia')
ON CONFLICT (nombre) DO NOTHING;

CREATE TABLE IF NOT EXISTS core.tipos_membresias (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- ======= Principales =======
CREATE TABLE IF NOT EXISTS core.usuarios (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150),
    email VARCHAR(150) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    fecha_nacimiento DATE,
    rol_id INT NOT NULL REFERENCES core.roles(id) ON DELETE RESTRICT,
    fecha_registro TIMESTAMPTZ DEFAULT now(),
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS core.clases (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    activa BOOLEAN DEFAULT TRUE
);

CREATE TABLE IF NOT EXISTS core.horarios_clases (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    clase_id INT REFERENCES core.clases(id) ON DELETE CASCADE,
    entrenador_id INT REFERENCES core.usuarios(id) ON DELETE SET NULL,
    fecha TIMESTAMPTZ NOT NULL,
    duracion_minutos INT CHECK (duracion_minutos > 0),
    capacidad_max INT CHECK (capacidad_max > 0),
    UNIQUE (clase_id, fecha)
);

CREATE TABLE IF NOT EXISTS core.reservas (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    horario_id INT REFERENCES core.horarios_clases(id) ON DELETE CASCADE,
    usuario_id INT REFERENCES core.usuarios(id) ON DELETE CASCADE,
    fecha_reserva TIMESTAMPTZ DEFAULT now(),
    estado VARCHAR(20) CHECK (estado IN ('reservada','cancelada','asistida','no_asistida')) DEFAULT 'reservada',
    UNIQUE (horario_id, usuario_id)
);

CREATE TABLE IF NOT EXISTS core.membresias (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id INT REFERENCES core.usuarios(id) ON DELETE CASCADE,
    tipo_id INT REFERENCES core.tipos_membresias(id) ON DELETE CASCADE,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    precio DECIMAL(8,2) NOT NULL,
    activa BOOLEAN DEFAULT TRUE,
    CHECK (fecha_fin > fecha_inicio)
);

CREATE TABLE IF NOT EXISTS core.pagos (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id INT REFERENCES core.usuarios(id) ON DELETE CASCADE,
    monto DECIMAL(8,2) NOT NULL,
    fecha TIMESTAMPTZ DEFAULT now(),
    metodo_id INT REFERENCES core.metodos_pago(id) ON DELETE RESTRICT,
    concepto VARCHAR(150)
);

CREATE TABLE IF NOT EXISTS core.ejercicios (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

CREATE TABLE IF NOT EXISTS core.benchmarks (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    usuario_id INT REFERENCES core.usuarios(id) ON DELETE CASCADE,
    ejercicio_id INT REFERENCES core.ejercicios(id) ON DELETE CASCADE,
    peso_max DECIMAL(6,2) NOT NULL CHECK (peso_max > 0),
    repeticiones INT DEFAULT 1 CHECK (repeticiones > 0),
    fecha_registro TIMESTAMPTZ DEFAULT now(),
    UNIQUE (usuario_id, ejercicio_id, repeticiones)
);

-- ======= Índices =======
CREATE INDEX IF NOT EXISTS idx_reservas_usuarios       ON core.reservas(usuario_id);
CREATE INDEX IF NOT EXISTS idx_reservas_clases         ON core.reservas(horario_id);
CREATE INDEX IF NOT EXISTS idx_reservas_estado         ON core.reservas(estado);

CREATE INDEX IF NOT EXISTS idx_horarios_clases_clase   ON core.horarios_clases(clase_id);
CREATE INDEX IF NOT EXISTS idx_horarios_clases_fecha   ON core.horarios_clases(fecha);
CREATE INDEX IF NOT EXISTS idx_horarios_clases_entrenador ON core.horarios_clases(entrenador_id);

CREATE INDEX IF NOT EXISTS idx_membresias_usuario      ON core.membresias(usuario_id);
CREATE INDEX IF NOT EXISTS idx_membresias_tipo         ON core.membresias(tipo_id);

CREATE INDEX IF NOT EXISTS idx_pagos_usuario           ON core.pagos(usuario_id);

CREATE INDEX IF NOT EXISTS idx_benchmarks_usuario      ON core.benchmarks(usuario_id);
CREATE INDEX IF NOT EXISTS idx_benchmarks_ejercicio    ON core.benchmarks(ejercicio_id);
