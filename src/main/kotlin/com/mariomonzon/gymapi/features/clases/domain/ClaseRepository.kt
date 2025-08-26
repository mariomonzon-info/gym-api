package com.mariomonzon.gymapi.features.clases.domain

interface ClaseRepository {
    fun findAll(): List<Clase>
    fun save(clase: Clase): Clase
}