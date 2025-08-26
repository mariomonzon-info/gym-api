package com.mariomonzon.gymapi.features.clases.infrastructure.persistence

import com.mariomonzon.gymapi.features.clases.domain.Clase
import com.mariomonzon.gymapi.features.clases.domain.ClaseRepository
import org.springframework.stereotype.Component

@Component
class ClasePersistenceAdapter(
    private val jpa: ClaseJpaRepository
) : ClaseRepository {

    override fun findAll(): List<Clase> = jpa.findAll().map { it.toDomain() }

    override fun save(clase: Clase): Clase = jpa.save( clase.toEntity() ).toDomain()

}

private fun ClaseEntity.toDomain() = Clase(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    activa = activa,
)

private fun Clase.toEntity() = ClaseEntity(
    id = id,
    nombre = nombre,
    descripcion = descripcion,
    activa = activa
)