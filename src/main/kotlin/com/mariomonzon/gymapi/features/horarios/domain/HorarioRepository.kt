package com.mariomonzon.gymapi.features.horarios.domain

import java.time.OffsetDateTime

interface HorarioRepository {
    fun findBetween(from: OffsetDateTime, to: OffsetDateTime, claseId: Int? = null): List<HorarioClase>
}