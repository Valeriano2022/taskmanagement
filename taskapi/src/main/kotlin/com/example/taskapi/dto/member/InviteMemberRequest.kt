package com.example.taskapi.dto.member

import jakarta.validation.constraints.NotBlank

data class InviteMemberRequest(
    @field:NotBlank
    val email: String   // sanitized in service
)
