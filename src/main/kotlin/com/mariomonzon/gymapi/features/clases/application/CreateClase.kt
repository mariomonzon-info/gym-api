package com.mariomonzon.gymapi.features.clases.application

import com.mariomonzon.gymapi.features.clases.domain.Clase
import com.mariomonzon.gymapi.features.clases.domain.ClaseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreateClase(private val claseRepository: ClaseRepository) {
    @Transactional
    fun handle(nombre: String, descripcion: String?, activa: Boolean): Clase{
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        return claseRepository.save(Clase(
            nombre = nombre.trim(),
            descripcion = descripcion?.trim(),
            activa = activa))
    }
}