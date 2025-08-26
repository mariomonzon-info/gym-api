package com.mariomonzon.gymapi.features.clases.infrastructure.web

import com.mariomonzon.gymapi.features.clases.application.CreateClase
import com.mariomonzon.gymapi.features.clases.application.ListClases
import com.mariomonzon.gymapi.features.clases.domain.ClaseRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/clases")
class ClaseController(
    private val listClases: ListClases,
    private val createClase: CreateClase,
) {
    @GetMapping
    fun list(): List<ClaseResponse> = listClases.handle().map { it.toResponse() }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody body: CreateClaseRequest): ClaseResponse = createClase.handle(
        body.nombre,
        body.descripcion,
        body.activa).toResponse()
}