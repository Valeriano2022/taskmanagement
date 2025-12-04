package com.example.taskapi.hateoas

import com.example.taskapi.controller.CommentController
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class CommentLinks {

    fun self(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(CommentController::class.java)
                .listComments( boardId, taskId)
        ).withSelfRel()

    fun create(boardId: Long, taskId: Long) =
        linkTo(
            methodOn(CommentController::class.java)
                .createComment(null, boardId, taskId, null)
        ).withRel("create")

    fun <T: Any> addTo(model: EntityModel<T>, boardId: Long, taskId: Long): EntityModel<T> =
        model
            .add(self(boardId, taskId))
            .add(create(boardId, taskId))
}

