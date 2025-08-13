package com.geoyeon.kotlin.todo.common

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log by logger()

    @ExceptionHandler(TodoException::class)
    fun handleTodoException(e: TodoException): ResponseEntity<ErrorResponse> {
        log.info("Todo Exception", e)

        val errorResponse: ErrorResponse = ErrorResponse.of(e.errorCode)

        return ResponseEntity.status(e.errorCode.httpStatus).body(errorResponse)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ResponseEntity<ErrorResponse> {
        log.info("Exception", e)

        val errorResponse: ErrorResponse = ErrorResponse.of(HttpStatus.INTERNAL_SERVER_ERROR.toString(), e.message ?: "",
            HttpStatus.INTERNAL_SERVER_ERROR)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponse)
    }
}