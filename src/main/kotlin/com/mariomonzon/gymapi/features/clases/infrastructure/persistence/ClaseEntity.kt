package com.mariomonzon.gymapi.features.clases.infrastructure.persistence

import jakarta.persistence.*


@Entity
@Table(name = "clases")
data class ClaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false)
    val nombre: String,

    @Column(columnDefinition = "text")
    val descripcion: String? = null,

    @Column(nullable = false)
    val activa: Boolean = true,
)
