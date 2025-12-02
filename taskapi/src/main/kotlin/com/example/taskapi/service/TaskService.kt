package com.example.taskapi.service

import com.example.taskapi.dto.task.AssignTaskRequest
import com.example.taskapi.dto.task.CreateTaskRequest
import com.example.taskapi.dto.task.TaskResponse
import com.example.taskapi.dto.task.UpdateTaskRequest
import com.example.taskapi.exception.TaskNotFoundException
import com.example.taskapi.exception.UserNotFoundException
import com.example.taskapi.model.Task
import com.example.taskapi.repository.BoardMemberRepository
import com.example.taskapi.repository.BoardRepository
import com.example.taskapi.repository.TaskRepository
import com.example.taskapi.repository.UserRepository
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
    private val  taskMapper: TaskMapper
) {

    @Transactional
    fun createTask(boardId: Long, userId: Long, request: CreateTaskRequest): TaskResponse {
        securityService.assertIsMember(boardId, userId)
        val board = boardRepository.findById(boardId).orElseThrow()
        val assignee = request.assigneeId?.let { userRepository.findById(it).orElse(null) }

        val saved =  taskRepository.save(
            Task(
                title = request.title.trim(),
                description = request.description?.trim(),
                board = board,
                assignee = assignee
            )
        )
        return taskMapper.toResponse(saved)
    }

    fun getTask(taskId: Long, boardId: Long, userId: Long): TaskResponse {
        securityService.assertIsMember(boardId, userId)
        val found =  taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }
        return taskMapper.toResponse(found)
    }

    @Transactional
    fun updateTask(taskId: Long, boardId: Long, userId: Long, request: UpdateTaskRequest): TaskResponse {
        securityService.assertIsMember(boardId, userId)
        val task = taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }

        task.title = request.title.trim()
        task.description = request.description?.trim()
        task.assignee = request.assigneeId?.let { userRepository.findById(it).orElse(null) }

        val saved = taskRepository.save(task)

        return taskMapper.toResponse(saved)
    }

    @Transactional
    fun assignTask(taskId: Long, boardId: Long, userId: Long, request: AssignTaskRequest): TaskResponse {
        securityService.assertIsMember(boardId, userId)

        val task = taskRepository.findById(taskId)
            .orElseThrow { TaskNotFoundException(taskId) }

        val assignee = userRepository.findById(request.assigneeId)
            .orElseThrow { UserNotFoundException(id = request.assigneeId) }

        val isAssigneeMember = boardMemberRepository.existsByBoardIdAndUserId(boardId, assignee.id!!)
        if (!isAssigneeMember) throw UserNotFoundException("User ${assignee.id} is not a member of this board")

        task.assignee = assignee
        val saved = taskRepository.save(task)

        return taskMapper.toResponse(saved)
    }

    @Transactional
    fun deleteTask(taskId: Long, boardId: Long, userId: Long) {
        securityService.assertIsMember(boardId, userId)
        taskRepository.deleteById(taskId)
    }

    fun listTasks(boardId: Long, userId: Long, pageable: Pageable): Page<TaskResponse> {
        securityService.assertIsMember(boardId, userId)
        return taskRepository.findAllByBoardId(boardId, pageable).map{taskMapper.toResponse(it)}
    }
}
