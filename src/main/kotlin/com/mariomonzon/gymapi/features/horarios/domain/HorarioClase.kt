package com.mariomonzon.gymapi.features.horarios.domain

import java.time.OffsetDateTime

data class HorarioClase(
    val id: Int? = null,
    val claseId: Int,
    val entrenadorId: Int? = null,
    val fecha: OffsetDateTime,
    val duracionMinutos: Int,
    val capacidadMax: Int,
)
