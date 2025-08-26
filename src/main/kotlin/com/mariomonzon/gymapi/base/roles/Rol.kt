package com.mariomonzon.gymapi.base.roles

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "roles")
data class Rol (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, unique = true)
    @NotBlank
    val nombre: String
)