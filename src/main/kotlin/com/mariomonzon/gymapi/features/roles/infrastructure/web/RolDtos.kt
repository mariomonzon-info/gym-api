package com.mariomonzon.gymapi.features.roles.infrastructure.web

import com.mariomonzon.gymapi.features.roles.domain.Rol
import jakarta.validation.constraints.NotBlank

data class RolResponse(val id: Int?, val nombre: String,)

data class CreateRolRequest(@field:NotBlank val nombre: String)

fun Rol.toResponse() = RolResponse(id, nombre)