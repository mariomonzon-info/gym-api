package com.mariomonzon.gymapi.features.horarios.infrastructure.persistence

import jakarta.persistence.*
import java.time.OffsetDateTime

@Entity
@Table(name = "horarios_clases")
data class HorarioEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(name = "clase_id", nullable = false)
    val claseId: Int,

    val entrenadorId: Int? = null,

    @Column(name = "fecha")
    val fecha: OffsetDateTime,

    @Column(name = "duracion_minutos", nullable = false)
    val duracionMinutos: Int,

    @Column(name = "capacidad_max", nullable = false)
    val capacidadMax: Int,
)