package com.example.taskapi.exception

class EmailAlreadyExistsException(email: String) :
    BaseException("Email already exists: $email", 409)