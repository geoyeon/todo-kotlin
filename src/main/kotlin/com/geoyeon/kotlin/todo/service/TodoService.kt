package com.geoyeon.kotlin.todo.service

import com.geoyeon.kotlin.todo.common.ErrorCode
import com.geoyeon.kotlin.todo.common.TodoException
import com.geoyeon.kotlin.todo.common.logger
import com.geoyeon.kotlin.todo.domain.Todo
import com.geoyeon.kotlin.todo.dto.TodoCreateRequest
import com.geoyeon.kotlin.todo.dto.TodoListResponse
import com.geoyeon.kotlin.todo.dto.TodoUpdateRequest
import com.geoyeon.kotlin.todo.repository.TodoRepository
import com.geoyeon.kotlin.todo.repository.TodoRepositoryCustom
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.Optional

@Service
class TodoService (
     private val todoRepository: TodoRepository,
    private val todoRepositoryCustom: TodoRepositoryCustom
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
        if (todoUpdateRequest.endDate != null) originalTodo.endDate = todoUpdateRequest.endDate
        if (todoUpdateRequest.isComplete != null) originalTodo.isComplete = todoUpdateRequest.isComplete

        log.info("before Save : $originalTodo")

        this.todoRepository.save<Todo>(originalTodo)

        return true;
    }

    fun getTodos(page: Int, isComplete: Boolean?, search: String?): TodoListResponse {
        val query: Query = Query()

        val limit: Int = 10
        val skip: Int = (page-1) * limit

        query.skip(skip.toLong())
        query.limit(limit)

        if (isComplete != null) {
            query.addCriteria(Criteria.where("isComplete").`is`(isComplete))
        }

        if (search != null && !search.isBlank()) {
            val orSearchCondition: ArrayList<Criteria> = ArrayList()
            orSearchCondition.add(Criteria.where("title").regex(search, "i"))
            orSearchCondition.add(Criteria.where("memo").regex(search, "i"))
            query.addCriteria(Criteria().orOperator(orSearchCondition))
        }

        log.info("query : $query")

        val totalCount: Long = if (isComplete == null && search == null) { this.todoRepositoryCustom.totalCount() } else { this.todoRepositoryCustom.countWithCondition(query) }

        val list: List<Todo> = this.todoRepositoryCustom.findByWherePaginationWithQuerydsl(query)

        return TodoListResponse(list, totalCount)
    }
}