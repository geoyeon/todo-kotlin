package com.geoyeon.kotlin.todo.domain

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "todos")
data class Todo (
    @Id
    var id: String? = null,

    @Field("title")
    var title: String? = null,

    @Field("memo")
    var memo: String? = null,

    @Field("startDate")
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var startDate: LocalDateTime? = null,

    @Field("endDate")
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var endDate: LocalDateTime? = null,

    @Field("isComplete")
    var isComplete: Boolean = false,

    @CreatedDate
    @get:JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createdAt: LocalDateTime? = null,

    @LastModifiedDate
    @get: JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updatedAt: LocalDateTime? = null,
) {}