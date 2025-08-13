package com.geoyeon.kotlin.todo.common

import org.springframework.http.HttpStatus

data class ErrorResponse(val code: String, val message: String, val httpStatus: HttpStatus) {
    constructor(errorCode: ErrorCode): this(errorCode.errorCode, errorCode.message, errorCode.httpStatus) {}

    companion object {
        fun of(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(errorCode)
        }

        fun of(code: String, message: String, httpStatus: HttpStatus): ErrorResponse {
            return ErrorResponse(code, message, httpStatus)
        }
    }
}
