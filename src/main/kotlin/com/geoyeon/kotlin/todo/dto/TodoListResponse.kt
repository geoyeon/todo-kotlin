package com.geoyeon.kotlin.todo.dto

import com.geoyeon.kotlin.todo.domain.Todo

data class TodoListResponse(
    val list: List<Todo>,
    val totalCount: Long
) {}
