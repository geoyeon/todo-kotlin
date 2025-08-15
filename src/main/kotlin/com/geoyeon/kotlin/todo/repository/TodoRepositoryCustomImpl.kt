package com.geoyeon.kotlin.todo.repository

import com.geoyeon.kotlin.todo.domain.Todo
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
class TodoRepositoryCustomImpl(private val mongoTemplate: MongoTemplate): TodoRepositoryCustom {

    override fun findByWherePaginationWithQuerydsl(query: Query): List<Todo> {
        val sort: Sort = Sort.by(Sort.Direction.DESC, "id")
        query.with(sort)

        return mongoTemplate.find(query, Todo::class.java)
    }

    override fun totalCount(): Long {
        return mongoTemplate.estimatedCount(Todo::class.java)
    }

    override fun countWithCondition(query: Query): Long {
        return mongoTemplate.count(query, Todo::class.java)
    }
}