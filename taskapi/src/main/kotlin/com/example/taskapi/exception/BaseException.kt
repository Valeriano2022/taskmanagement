package com.example.taskapi.exception

open class BaseException(
    override val message: String,
    val status: Int
) : RuntimeException(message)