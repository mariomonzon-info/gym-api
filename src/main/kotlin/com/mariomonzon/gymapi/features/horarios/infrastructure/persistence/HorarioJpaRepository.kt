package com.mariomonzon.gymapi.features.horarios.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository
import java.time.OffsetDateTime

interface HorarioJpaRepository : JpaRepository<HorarioEntity, Int> {
    fun findByFechaBetweenOrderByFechaAsc(from: OffsetDateTime, to: OffsetDateTime): List<HorarioEntity>
    fun findByClaseIdAndFechaBetweenOrderByFechaAsc(claseId: Int, from: OffsetDateTime, to: OffsetDateTime): List<HorarioEntity>
}