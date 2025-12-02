package com.example.studentapi.util

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class AccountGenerator(
    private val passwordEncoder: PasswordEncoder
){

    fun generateUniversityEmail(name: String): String {
        val cleaned = name
            .trim()
            .lowercase()
            .replace("\\s+".toRegex(), "")
        return "$cleaned@university.edu.ph"
    }

    fun generateTemporaryPassword(length: Int = 10): String{
        val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#\$%!?"
        return (1..length)
            .map { chars.random() }
            .joinToString("")
    }
}