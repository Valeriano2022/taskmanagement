package com.example.taskapi.controller

import com.example.taskapi.dto.member.BoardMemberResponse
import com.example.taskapi.dto.member.InviteMemberRequest
import com.example.taskapi.hateoas.MemberLinks
import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.service.MemberService
import com.example.taskapi.utils.mapper.BoardMemberMapper
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/boards/{boardId}/members")
class MemberController(
    private val memberService: MemberService,
    private val memberLinks: MemberLinks
) {

    @PostMapping("/invite")
    fun inviteMember(
        authentication: Authentication,
        @PathVariable boardId: Long,
        @Valid @RequestBody request: InviteMemberRequest
    ) : ResponseEntity<EntityModel<Map<String, String>>> {
        val principal = authentication.principal as CustomUserPrincipal
        memberService.inviteMember(boardId, principal.userId, request)
        return ResponseEntity.ok(EntityModel.of(mapOf("message" to "Member invited")))
    }

    @GetMapping
    fun listMembers(authentication: Authentication, @PathVariable boardId: Long)
            : ResponseEntity<CollectionModel<EntityModel<BoardMemberResponse>>> {
        val principal = authentication.principal as CustomUserPrincipal
        val members = memberService.listMembers(boardId, principal.userId).map { it }
        val models = members.map { EntityModel.of(it).add(memberLinks.remove(boardId, it.user.id)) }
        return ResponseEntity.ok(CollectionModel.of(models).add(memberLinks.self(boardId)))
    }

    @DeleteMapping("/{userId}")
    fun removeMember(authentication: Authentication, @PathVariable boardId: Long, @PathVariable userId: Long)
            : ResponseEntity<Void> {
        val principal = authentication.principal as CustomUserPrincipal
        memberService.removeMember(boardId, principal.userId, userId)
        return ResponseEntity.noContent().build()
    }
}
