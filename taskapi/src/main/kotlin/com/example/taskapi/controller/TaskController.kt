package com.example.taskapi.controller

import com.example.taskapi.dto.task.AssignTaskRequest
import com.example.taskapi.dto.task.CreateTaskRequest
import com.example.taskapi.dto.task.TaskResponse
import com.example.taskapi.dto.task.UpdateTaskRequest
import com.example.taskapi.hateoas.TaskLinks
import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.service.TaskService
import com.example.taskapi.utils.cast
import com.example.taskapi.utils.mapper.TaskMapper
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/boards/{boardId}/tasks")
class TaskController(
    private val taskService: TaskService,
    private val taskMapper: TaskMapper,
    private val taskLinks: TaskLinks,
    private val pagedAssembler: PagedResourcesAssembler<TaskResponse>
) {

    @PostMapping
    fun createTask(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @Valid @RequestBody
        request: CreateTaskRequest
    ) : ResponseEntity<EntityModel<TaskResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val task = taskService.createTask(boardId, principal.userId, request)
        return ResponseEntity.ok(taskLinks
            .addTo(EntityModel.of(task), boardId, task.id))
    }

    @GetMapping("/{taskId}")
    fun getTask(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @PathVariable taskId: Long
    ) : ResponseEntity<EntityModel<TaskResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val task = taskService.getTask(taskId, boardId, principal.userId)
        return ResponseEntity.ok(taskLinks.addTo(EntityModel
            .of(task), boardId, taskId))
    }

    @PutMapping("/{taskId}")
    fun updateTask(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @PathVariable taskId: Long,
        @Valid @RequestBody
        request: UpdateTaskRequest)
            : ResponseEntity<EntityModel<TaskResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val task = taskService.updateTask(taskId, boardId, principal.userId, request)
        return ResponseEntity.ok(taskLinks.addTo(EntityModel
            .of(task), boardId, taskId))
    }

    @PutMapping("/{taskId}/assign")
    fun assignTask(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @PathVariable taskId: Long,
        @RequestBody request: AssignTaskRequest
    ): ResponseEntity<EntityModel<TaskResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val task = taskService.assignTask(taskId, boardId, principal.userId, request)
        return ResponseEntity.ok(taskLinks.addTo(EntityModel
            .of(task), boardId, taskId))
    }

    @DeleteMapping("/{taskId}")
    fun deleteTask(authentication: Authentication, @PathVariable boardId: Long, @PathVariable taskId: Long)
            : ResponseEntity<EntityModel<Map<String, String>>> {
        val principal = authentication.principal as CustomUserPrincipal
        taskService.deleteTask(taskId, boardId, principal.userId)
        return ResponseEntity.ok(EntityModel.of(mapOf("message" to "Task deleted")))
    }

    @GetMapping
    fun listTasks(
        authentication: Authentication,
        @PathVariable boardId: Long,
        pageable: Pageable
    ): ResponseEntity<PagedModel<EntityModel<TaskResponse>>> {
        val principal = authentication.principal as CustomUserPrincipal
        val page = taskService.listTasks(boardId, principal.userId, pageable).map { it }
        val model = pagedAssembler.toModel(page)
        { dto -> taskLinks.addTo(EntityModel.of(dto), boardId, dto.id) }
            .cast<TaskResponse>()
        return ResponseEntity.ok(model)
    }
}
