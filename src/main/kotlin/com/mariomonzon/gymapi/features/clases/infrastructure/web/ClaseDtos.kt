package com.mariomonzon.gymapi.features.clases.infrastructure.web

import com.mariomonzon.gymapi.features.clases.domain.Clase
import jakarta.validation.constraints.NotBlank

data class ClaseResponse(val id: Int?, val nombre: String, val descripcion: String?, val activa: Boolean)
data class CreateClaseRequest(@field:NotBlank val nombre: String, val descripcion: String? = null, val activa: Boolean = true)

fun Clase.toResponse() = ClaseResponse(id, nombre, descripcion, activa)
