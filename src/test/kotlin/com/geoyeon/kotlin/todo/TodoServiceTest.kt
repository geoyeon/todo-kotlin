package com.geoyeon.kotlin.todo

import com.geoyeon.kotlin.todo.domain.Todo
import com.geoyeon.kotlin.todo.dto.TodoCreateRequest
import com.geoyeon.kotlin.todo.dto.TodoListResponse
import com.geoyeon.kotlin.todo.repository.TodoRepository
import com.geoyeon.kotlin.todo.repository.TodoRepositoryCustom
import com.geoyeon.kotlin.todo.service.TodoService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.kotlin.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.junit.jupiter.MockitoSettings
import org.mockito.quality.Strictness
import org.springframework.data.mongodb.core.query.Query
import java.time.LocalDateTime
import kotlin.test.Test


@ExtendWith(MockitoExtension::class)
class TodoServiceTest() {
    @InjectMocks
    private lateinit var todoService: TodoService
    @field:Mock
    private lateinit var todoRepository: TodoRepository
    @field:Mock
    private lateinit var todoRepositoryCustom: TodoRepositoryCustom

    @BeforeEach
    fun beforeEach() {}

    @Test
    @DisplayName("TODO 생성 성공")
    fun successTodoCreate() {
        val title: String = "제목"
        val memo: String = "메모"
        val startDate: LocalDateTime = LocalDateTime.of(2015, 8,15, 23, 31)
        val endDate: LocalDateTime = LocalDateTime.of(2015, 8,15, 23, 32)
        val request: TodoCreateRequest = TodoCreateRequest(title, memo, startDate, endDate)

        val mockTodo = Todo(request.title, request.memo, request.startDate, request.endDate)

        doReturn(mockTodo).`when`<TodoRepository>(this.todoRepository).save(any<Todo>())

        var sut = TodoService(this.todoRepository, this.todoRepositoryCustom)

        val savedTodo: Todo = sut.createTodo(request)

        assertThat(savedTodo.title).isEqualTo("제목")
        assertThat(savedTodo.memo).isEqualTo("메모")
        assertThat(savedTodo.startDate).isEqualTo(LocalDateTime.of(2015, 8, 15, 23, 31))
        assertThat(savedTodo.endDate).isEqualTo(LocalDateTime.of(2015, 8, 15, 23, 32))
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    @DisplayName("TODO 목록 조회")
    fun successTodoList() {
        val page: Int = 1

        val mockTodos: ArrayList<Todo> = ArrayList()
        val mockTotalCount: Long = 0

        doReturn(mockTotalCount).`when`(this.todoRepositoryCustom).totalCount()
        doReturn(mockTodos).`when`(this.todoRepositoryCustom).findByWherePaginationWithQuerydsl(any<Query>())

        var sut = TodoService(this.todoRepository, this.todoRepositoryCustom)

        val response: TodoListResponse = sut.getTodos(page, null, null)

        assertThat(response.list).isEqualTo(mockTodos)
        assertThat(response.totalCount).isEqualTo(mockTotalCount)

        verify(this.todoRepositoryCustom, times(1)).totalCount();
        verify(this.todoRepositoryCustom, times(1)).findByWherePaginationWithQuerydsl(any<Query>())
    }
}