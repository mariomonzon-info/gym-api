package com.mariomonzon.gymapi.features.roles.application

import com.mariomonzon.gymapi.features.roles.domain.Rol
import com.mariomonzon.gymapi.features.roles.domain.RolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateRol(private val rolRepository: RolRepository) {

    @Transactional
    fun handle(nombre: String): Rol{
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        return rolRepository.save(Rol(nombre = nombre.trim()))
    }
}