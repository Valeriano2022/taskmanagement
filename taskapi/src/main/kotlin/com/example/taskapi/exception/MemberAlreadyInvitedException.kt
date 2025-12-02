package com.example.taskapi.exception

class MemberAlreadyInvitedException(email: String) :
    BaseException("User already a member: $email", 409)