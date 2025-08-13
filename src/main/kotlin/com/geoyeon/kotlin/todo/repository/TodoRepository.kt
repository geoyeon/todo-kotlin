package com.geoyeon.kotlin.todo.repository

import com.geoyeon.kotlin.todo.domain.Todo
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TodoRepository: MongoRepository<Todo, ObjectId> {
}