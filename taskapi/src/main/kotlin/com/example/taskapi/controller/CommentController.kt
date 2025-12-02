package com.example.taskapi.controller

import com.example.taskapi.dto.comment.CreateCommentRequest
import com.example.taskapi.dto.comment.TaskCommentResponse
import com.example.taskapi.hateoas.CommentLinks
import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.service.CommentService
import com.example.taskapi.utils.mapper.CommentMapper
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/tasks/{taskId}/comments")
class CommentController(
    private val commentService: CommentService,
    private val commentLinks: CommentLinks
) {

    @PostMapping
    fun createComment(authentication: Authentication, @PathVariable taskId: Long, @Valid @RequestBody request: CreateCommentRequest)
            : ResponseEntity<EntityModel<TaskCommentResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val comment = commentService.createComment(taskId, principal.userId, request)
        return ResponseEntity.ok(commentLinks.addTo(EntityModel.of(comment), taskId))
    }

    @GetMapping
    fun listComments(@PathVariable taskId: Long)
            : ResponseEntity<CollectionModel<EntityModel<TaskCommentResponse>>> {
        val comments = commentService.listComments(taskId).map { it }
        val models = comments.map { EntityModel.of(it) }
        return ResponseEntity.ok(CollectionModel.of(models).add(commentLinks.self(taskId)))
    }
}
