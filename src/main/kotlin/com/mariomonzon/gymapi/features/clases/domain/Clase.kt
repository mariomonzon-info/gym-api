package com.mariomonzon.gymapi.features.clases.domain

data class Clase(
    val id: Int? = null,
    val nombre: String,
    val descripcion: String? = null,
    val activa: Boolean = true,
)
