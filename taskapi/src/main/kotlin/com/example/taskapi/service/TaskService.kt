package com.example.taskapi.service

import com.example.taskapi.dto.task.AssignTaskRequest
import com.example.taskapi.dto.task.CreateTaskRequest
import com.example.taskapi.dto.task.TaskResponse
import com.example.taskapi.dto.task.UpdateTaskRequest
import com.example.taskapi.dto.websocket.TaskEvent
import com.example.taskapi.exception.TaskNotFoundException
import com.example.taskapi.exception.UserNotFoundException
import com.example.taskapi.model.Task
import com.example.taskapi.repository.*
import com.example.taskapi.utils.mapper.TaskMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TaskService(
    private val taskRepository: TaskRepository,
    private val boardRepository: BoardRepository,
    private val userRepository: UserRepository,
    private val securityService: SecurityService,
    private val boardMemberRepository: BoardMemberRepository,
    private val taskMapper: TaskMapper,
    private val notifications: NotificationService
) {

    @Transactional
    fun createTask(boardId: Long, userId: Long, request: CreateTaskRequest): TaskResponse {
        securityService.assertIsMember(boardId, userId)

        val board = boardRepository.findById(boardId).orElseThrow()
        val assignee = request.assigneeId?.let { userRepository.findById(it).orElse(null) }

        val saved = taskRepository.save(
            Task(
                title = request.title.trim(),
                description = request.description?.trim(),
                board = board,
                assignee = assignee,
                status = request.status,
                priority = request.priority,
            )
        )

        val dto = taskMapper.toResponse(saved)
        notifications.taskEvent(boardId, saved.id!!, TaskEvent("TASK_CREATED", dto))

        return dto
    }

    fun getTask(taskId: Long, boardId: Long, userId: Long): TaskResponse {
        securityService.assertIsMember(boardId, userId)
        val found = taskRepository.findById(taskId).orElseThrow { TaskNotFoundException(taskId) }
        return taskMapper.toResponse(found)
    }

    @Transactional
    fun updateTask(taskId: Long, boardId: Long, userId: Long, request: UpdateTaskRequest?): TaskResponse {
        securityService.assertIsMember(boardId, userId)

        val task = taskRepository.findById(taskId).orElseThrow { TaskNotFoundException(taskId) }

        task.title = request?.title?.trim().toString()
        task.description = request?.description?.trim()
        task.assignee = request?.assigneeId?.let { userRepository.findById(it).orElse(null) }

        val saved = taskRepository.save(task)
        val dto = taskMapper.toResponse(saved)

        notifications.taskEvent(boardId, taskId, TaskEvent("TASK_UPDATED", dto))

        return dto
    }

    @Transactional
    fun assignTask(taskId: Long, boardId: Long, userId: Long, request: AssignTaskRequest): TaskResponse {
        securityService.assertIsMember(boardId, userId)

        val task = taskRepository.findById(taskId).orElseThrow { TaskNotFoundException(taskId) }

        val assignee = userRepository.findById(request.assigneeId)
            .orElseThrow { UserNotFoundException(id = request.assigneeId) }

        if (!boardMemberRepository.existsByBoardIdAndUserId(boardId, assignee.id!!)) {
            throw UserNotFoundException("User ${assignee.id} is not a board member")
        }

        task.assignee = assignee

        val saved = taskRepository.save(task)
        val dto = taskMapper.toResponse(saved)

        notifications.taskEvent(boardId, taskId, TaskEvent("TASK_ASSIGNED", dto))

        return dto
    }

    @Transactional
    fun deleteTask(taskId: Long, boardId: Long, userId: Long) {
        securityService.assertIsMember(boardId, userId)

        taskRepository.deleteById(taskId)

        notifications.taskEvent(boardId, taskId, TaskEvent("TASK_DELETED"))
    }

    @Transactional(readOnly = true)
    fun listTasks(boardId: Long, userId: Long, pageable: Pageable): Page<TaskResponse> {
        securityService.assertIsMember(boardId, userId)
        return taskRepository.findAllByBoardId(boardId, pageable)
            .map { taskMapper.toResponse(it) }
    }
}