package com.example.taskapi

import com.example.taskapi.config.JpaConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration
import org.springframework.context.annotation.Import
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication(exclude = [UserDetailsServiceAutoConfiguration::class])
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Import(JpaConfig::class)
class TaskapiApplication

fun main(args: Array<String>) {
	runApplication<TaskapiApplication>(*args)
}
