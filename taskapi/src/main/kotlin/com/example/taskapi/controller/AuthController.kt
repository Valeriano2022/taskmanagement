package com.example.taskapi.controller

import com.example.taskapi.dto.auth.LoginRequest
import com.example.taskapi.dto.auth.RefreshTokenRequest
import com.example.taskapi.dto.auth.SignupRequest
import com.example.taskapi.dto.auth.LoginResponse
import com.example.taskapi.dto.auth.RefreshResponse
import com.example.taskapi.service.AuthService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.hateoas.EntityModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/signup")
    fun signUp(@Valid @RequestBody request: SignupRequest): ResponseEntity<EntityModel<Map<String, String>>> {
        authService.signup(request)
        return ResponseEntity.ok(EntityModel.of(mapOf("message" to "Signup successful")))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request: LoginRequest, response: HttpServletResponse): ResponseEntity<EntityModel<LoginResponse>> {
        val response = authService.login(request, response)
        return ResponseEntity.ok(EntityModel.of(response))
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<EntityModel<RefreshResponse>> {
        val response = authService.refresh(request)
        return ResponseEntity.ok(EntityModel.of(response))
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest, response: HttpServletResponse): ResponseEntity<String> {
        authService.logout(request, response)
        return ResponseEntity.noContent().build()
    }
}
