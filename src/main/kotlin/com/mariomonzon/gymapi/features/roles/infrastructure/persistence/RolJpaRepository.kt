package com.mariomonzon.gymapi.features.roles.infrastructure.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface RolJpaRepository : JpaRepository<RolEntity, Int>