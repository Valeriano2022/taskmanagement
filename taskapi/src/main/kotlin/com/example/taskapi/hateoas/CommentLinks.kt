package com.example.taskapi.hateoas

import com.example.taskapi.controller.CommentController
import org.springframework.hateoas.Link
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class CommentLinks {

    fun self(taskId: Long) =
        linkTo(CommentController::class.java)
            .slash(taskId)
            .slash("comments")
            .withSelfRel()

    fun create(taskId: Long) =
        linkTo(CommentController::class.java)
            .slash(taskId)
            .slash("comments")
            .withRel("create")

    fun <T : Any> addTo(model: EntityModel<T>, taskId: Long): EntityModel<T> =
        model.add(self(taskId), create(taskId))
}
