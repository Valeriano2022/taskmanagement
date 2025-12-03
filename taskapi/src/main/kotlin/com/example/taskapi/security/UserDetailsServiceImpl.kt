package com.example.taskapi.security

import com.example.taskapi.security.CustomUserPrincipal
import com.example.taskapi.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found: $email")

        return CustomUserPrincipal(
            userId = user.id!!,
            email = user.email,
            password = user.password

        )
    }
}
