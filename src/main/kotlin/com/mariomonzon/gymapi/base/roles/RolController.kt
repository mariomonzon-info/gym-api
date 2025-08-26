package com.mariomonzon.gymapi.base.roles

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/roles")
class RolController(private val rolRepository: RolRepository) {

    @GetMapping
    fun list(): List<Rol> = rolRepository.findAll().sortedBy { it.id }
}