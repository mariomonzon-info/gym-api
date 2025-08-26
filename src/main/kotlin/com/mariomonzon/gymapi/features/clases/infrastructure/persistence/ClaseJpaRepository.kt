package com.mariomonzon.gymapi.features.clases.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface ClaseJpaRepository: JpaRepository<ClaseEntity, Int>