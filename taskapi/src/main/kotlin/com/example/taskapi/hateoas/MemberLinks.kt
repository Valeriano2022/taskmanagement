package com.example.taskapi.hateoas

import com.example.taskapi.controller.MemberController
import org.springframework.hateoas.Link
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class MemberLinks {

    fun self(boardId: Long) =
        linkTo(MemberController::class.java)
            .slash(boardId)
            .slash("members")
            .withSelfRel()

    fun invite(boardId: Long) =
        linkTo(MemberController::class.java)
            .slash(boardId)
            .slash("members")
            .slash("invite")
            .withRel("invite")

    fun remove(boardId: Long, userId: Long) =
        linkTo(MemberController::class.java)
            .slash(boardId)
            .slash("members")
            .slash(userId)
            .withRel("remove")

    fun <T : Any> addTo(model: EntityModel<T>, boardId: Long): EntityModel<T> =
        model.add(self(boardId), invite(boardId))
}
