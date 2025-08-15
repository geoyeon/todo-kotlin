package com.geoyeon.kotlin.todo.controller

import com.geoyeon.kotlin.todo.common.ErrorCode
import com.geoyeon.kotlin.todo.common.TodoException
import com.geoyeon.kotlin.todo.common.logger
import com.geoyeon.kotlin.todo.domain.Todo
import com.geoyeon.kotlin.todo.dto.TodoCreateRequest
import com.geoyeon.kotlin.todo.dto.TodoListResponse
import com.geoyeon.kotlin.todo.dto.TodoUpdateRequest
import com.geoyeon.kotlin.todo.service.TodoService
import jakarta.validation.Valid
import org.springframework.data.mongodb.config.EnableMongoAuditing
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@EnableMongoAuditing
@RequestMapping("/todos")
class TodoController(private val todoService: TodoService) {
    private val logger by logger()

    @PostMapping("/")
    fun create(@RequestBody @Valid todoCreateRequest: TodoCreateRequest):ResponseEntity<Todo> {
        logger.info("Post : Todo Create - $todoCreateRequest")

        val todo: Todo = this.todoService.createTodo(todoCreateRequest)
        return ResponseEntity.ok().body(todo)
    }

    @GetMapping("/{id}")
    fun getTodo(@PathVariable("id") id: String): ResponseEntity<Todo> {
        logger.info("Get : Todo Id - $id")

        val todo: Todo = this.todoService.getTodo(id).orElseThrow { throw TodoException(ErrorCode.NOT_FOUND_TODO) }

        return ResponseEntity.ok().body(todo)
    }

    @PatchMapping("/{id}")
    fun updateTodo(@PathVariable("id") id:String, @RequestBody @Valid todoUpdateRequest: TodoUpdateRequest): ResponseEntity<Void> {
        logger.info("update : todo - $id : $todoUpdateRequest")

        this.todoService.updateTodo(id, todoUpdateRequest)

        return ResponseEntity.noContent().build();
    }
    @GetMapping("/")
    fun getTodos(@RequestParam(value = "page", required = false) page: Int = 1, @RequestParam(value = "isComplete", required = false) isComplete: Boolean?, @RequestParam(value = "search", required = false) search: String? ): ResponseEntity<TodoListResponse> {
        logger.info("Get : List : $page : $isComplete : $search")

        val result: TodoListResponse = this.todoService.getTodos(page, isComplete, search)

        return ResponseEntity.ok().body(result)
    }
}