package com.example.bk_xsports_app_v2.network.request

data class RegisterRequest(
    val nickname: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)