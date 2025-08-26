package com.mariomonzon.gymapi.common.error

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestExceptionHandler {

    data class ErrorPayload(val message: String)

    @ExceptionHandler(IllegalArgumentException::class)
    fun badRequest(e: IllegalArgumentException) = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorPayload(e.message ?: "Bad request"))

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validation(e: MethodArgumentNotValidException): ResponseEntity<ErrorPayload> {
        val msg = e.bindingResult.fieldErrors.joinToString { "${it.field}: ${it.defaultMessage}" }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorPayload(msg.ifBlank { "Validation error" }))
    }
}


/*

curl -s -X POST http://localhost:8080/api/clases \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Entrenamiento Funcional","descripcion":"Clase de entrenamiento funcional","activa":true}' | jq

 */