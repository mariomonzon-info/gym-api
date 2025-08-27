package com.mariomonzon.gymapi.features.horarios.infrastructure.persistence

import com.mariomonzon.gymapi.features.horarios.domain.HorarioClase
import com.mariomonzon.gymapi.features.horarios.domain.HorarioRepository
import org.springframework.stereotype.Component
import java.time.OffsetDateTime


@Component
class HorarioPersistenceAdapter(
    private val jpa: HorarioJpaRepository
) : HorarioRepository {

    override fun findBetween(
        from: OffsetDateTime,
        to: OffsetDateTime,
        claseId: Int?
    ): List<HorarioClase> =
        if (claseId == null)
            jpa.findByFechaBetweenOrderByFechaAsc(from, to).map { it.toDomain() }
        else
            jpa.findByClaseIdAndFechaBetweenOrderByFechaAsc(claseId, from, to).map { it.toDomain() }
}

private fun HorarioEntity.toDomain() = HorarioClase(
    id = id,
    claseId = claseId,
    entrenadorId = entrenadorId,
    fecha = fecha,
    duracionMinutos = duracionMinutos,
    capacidadMax = capacidadMax,
)
