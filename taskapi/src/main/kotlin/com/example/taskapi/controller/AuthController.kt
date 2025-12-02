package com.example.taskapi.controller

import com.example.taskapi.dto.auth.LoginRequest
import com.example.taskapi.dto.auth.RefreshTokenRequest
import com.example.taskapi.dto.auth.SignupRequest
import com.example.taskapi.dto.auth.LoginResponse
import com.example.taskapi.dto.auth.LogoutResponse
import com.example.taskapi.dto.auth.RefreshResponse
import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.service.AuthService
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signup(@Valid @RequestBody request: SignupRequest): ResponseEntity<EntityModel<Map<String, String>>> {
        authService.signup(request)
        return ResponseEntity.ok(EntityModel.of(mapOf("message" to "Signup successful")))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest): ResponseEntity<EntityModel<LoginResponse>> {
        val response = authService.login(request)
        return ResponseEntity.ok(EntityModel.of(response))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<EntityModel<RefreshResponse>> {
        val response = authService.refresh(request)
        return ResponseEntity.ok(EntityModel.of(response))
    }

    @PostMapping("/logout")
    fun logout(authentication: Authentication): ResponseEntity<EntityModel<LogoutResponse>> {
        val principal = authentication.principal as CustomUserPrincipal
        val response = authService.logout(principal.userId)
        return ResponseEntity.ok(EntityModel.of(response))
    }
}
