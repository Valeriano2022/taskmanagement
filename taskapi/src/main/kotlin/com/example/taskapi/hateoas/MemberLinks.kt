package com.example.taskapi.hateoas

import com.example.taskapi.controller.MemberController
import org.springframework.hateoas.Link
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*
import org.springframework.stereotype.Component

@Component
class MemberLinks {

    fun self(boardId: Long) =
        linkTo(
            methodOn(MemberController::class.java)
                .listMembers(null, boardId)
        ).withSelfRel()

    fun invite(boardId: Long) =
        linkTo(
            methodOn(MemberController::class.java)
                .inviteMember(null, boardId, null)
        ).withRel("invite")

    fun remove(boardId: Long, userId: Long) =
        linkTo(
            methodOn(MemberController::class.java)
                .removeMember(null, boardId, userId)
        ).withRel("remove")

    fun <T: Any> addTo(model: EntityModel<T>, boardId: Long): EntityModel<T> =
        model
            .add(self(boardId))
            .add(invite(boardId))
}

