package com.geoyeon.kotlin.todo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class TodoUpdateRequest(
    val title: String?,
    val memo: String?,

    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val startDate: LocalDateTime?,

    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val endDate: LocalDateTime?,

    val isComplete: Boolean?
) {}
