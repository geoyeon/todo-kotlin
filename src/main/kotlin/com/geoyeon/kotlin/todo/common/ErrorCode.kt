package com.geoyeon.kotlin.todo.common

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String
) {
    NOT_FOUND_TODO(HttpStatus.NOT_FOUND, "NOT_FOUND_TODO", "해당 todo를 찾을 수 없습니다.")
}