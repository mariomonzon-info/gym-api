package com.mariomonzon.gymapi.features.roles.infrastructure.persistence

import com.mariomonzon.gymapi.features.roles.domain.Rol
import com.mariomonzon.gymapi.features.roles.domain.RolRepository
import org.springframework.stereotype.Component

@Component
class RolPersistenceAdapter(
    private val jpa: RolJpaRepository
): RolRepository {
    override fun findAll(): List<Rol> = jpa.findAll().map { it.toDomain() }

    override fun save(rol: Rol): Rol = jpa.save(rol.toEntity()).toDomain()
}

private fun RolEntity.toDomain() = Rol(
    id =id,
    nombre = nombre,
)

private fun Rol.toEntity() = RolEntity(
    id = id,
    nombre = nombre,
)