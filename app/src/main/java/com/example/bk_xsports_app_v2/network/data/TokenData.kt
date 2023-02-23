package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenData(
    val data: Token
)

data class Token(
    val token: String
)