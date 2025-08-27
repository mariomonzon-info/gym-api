package com.mariomonzon.gymapi.features.roles.application

import com.mariomonzon.gymapi.features.roles.domain.Rol
import com.mariomonzon.gymapi.features.roles.domain.RolRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListRoles(private val rolRepository: RolRepository) {

    @Transactional(readOnly = true)
    fun handle(): List<Rol> = rolRepository.findAll().sortedBy { it.id }

}