package com.geoyeon.kotlin.todo.common

class TodoException(val errorCode: ErrorCode): RuntimeException(errorCode.message) {
}