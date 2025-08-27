package com.mariomonzon.gymapi.features.roles.domain


interface RolRepository {

    fun findAll(): List<Rol>
    fun save(rol: Rol): Rol
}