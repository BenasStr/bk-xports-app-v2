package com.example.bk_xsports_app_v2.network.data

import android.media.session.MediaSession.Token
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TokenData(
    val data: Token
)

data class Token(
    val token: String
)