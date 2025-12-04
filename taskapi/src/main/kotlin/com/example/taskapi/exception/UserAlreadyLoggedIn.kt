package com.example.taskapi.exception

class UserAlreadyLoggedIn(id: Long):
    BaseException("User already logged in: $id", 400)