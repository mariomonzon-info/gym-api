package com.mariomonzon.gymapi.features.roles.infrastructure.web

import com.mariomonzon.gymapi.features.roles.application.CreateRol
import com.mariomonzon.gymapi.features.roles.application.ListRoles
import com.mariomonzon.gymapi.features.roles.domain.Rol
import com.mariomonzon.gymapi.features.roles.domain.RolRepository
import com.mariomonzon.gymapi.features.roles.infrastructure.persistence.RolJpaRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RolController(
    private val listRoles: ListRoles,
    private val createRol: CreateRol,
) {

    @GetMapping
    fun list(): List<RolResponse> = listRoles.handle().map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: CreateRolRequest): RolResponse = createRol.handle(
        body.nombre,
    ).toResponse()
}