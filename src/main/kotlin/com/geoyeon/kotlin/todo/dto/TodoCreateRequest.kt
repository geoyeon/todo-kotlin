package com.geoyeon.kotlin.todo.dto

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class TodoCreateRequest(
    @field:NotBlank(message = "제목은 필수입니다.")
    val title: String,

    val memo: String,

    @field:NotNull(message = "시작일자를 입력해주세요")
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val startDate: LocalDateTime,

    @field:NotNull(message = "종료일자를 입력해주세요")
    @field:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    val endDate: LocalDateTime,
) {}
