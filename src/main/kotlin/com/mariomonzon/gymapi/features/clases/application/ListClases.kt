package com.mariomonzon.gymapi.features.clases.application

import com.mariomonzon.gymapi.features.clases.domain.Clase
import com.mariomonzon.gymapi.features.clases.domain.ClaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ListClases(private val claseRepository: ClaseRepository) {
    @Transactional(readOnly = true)
    fun handle(): List<Clase> = claseRepository.findAll().sortedBy { it.id }
}