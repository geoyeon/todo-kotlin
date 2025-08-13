package com.geoyeon.kotlin.todo.service

import com.geoyeon.kotlin.todo.common.ErrorCode
import com.geoyeon.kotlin.todo.common.TodoException
import com.geoyeon.kotlin.todo.common.logger
import com.geoyeon.kotlin.todo.domain.Todo
import com.geoyeon.kotlin.todo.dto.TodoCreateRequest
import com.geoyeon.kotlin.todo.dto.TodoUpdateRequest
import com.geoyeon.kotlin.todo.repository.TodoRepository
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TodoService (
     private val todoRepository: TodoRepository,
) {
    private val log by logger()

    fun createTodo(request: TodoCreateRequest) : Todo {
        val todo: Todo = Todo()
        todo.title = request.title
        todo.memo = request.memo
        todo.startDate = request.startDate
        todo.endDate = request.endDate

        return this.todoRepository.save<Todo>(todo)
    }

    fun getTodo(id: String): Optional<Todo> {
        return this.todoRepository.findById(ObjectId(id))
    }

    fun updateTodo(id: String, todoUpdateRequest: TodoUpdateRequest): Boolean {
        val originalTodo = this.getTodo(id).orElseThrow { TodoException(ErrorCode.NOT_FOUND_TODO) }

        if (todoUpdateRequest.title != null) originalTodo.title = todoUpdateRequest.title
        if (todoUpdateRequest.memo != null) originalTodo.memo = todoUpdateRequest.memo
        if (todoUpdateRequest.startDate != null) originalTodo.startDate = todoUpdateRequest.startDate
        if (todoUpdateRequest.endDate != null) originalTodo.startDate = todoUpdateRequest.endDate
        if (todoUpdateRequest.isComplete != null) originalTodo.isComplete = todoUpdateRequest.isComplete

        log.info("before Save : $originalTodo")

        this.todoRepository.save<Todo>(originalTodo)

        return true;
    }
}