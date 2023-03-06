package com.example.bk_xsports_app_v2.network.request

data class RegisterRequest(
    val name: String,
    val surname: String,
    val nickname: String,
    val email: String,
    val password: String
)