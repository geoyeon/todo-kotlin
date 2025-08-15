package com.geoyeon.kotlin.todo.repository

import com.geoyeon.kotlin.todo.domain.Todo
import org.springframework.data.mongodb.core.query.Query

interface TodoRepositoryCustom {
    fun findByWherePaginationWithQuerydsl(query: Query): List<Todo>

    fun totalCount(): Long

    fun countWithCondition(query: Query): Long
}