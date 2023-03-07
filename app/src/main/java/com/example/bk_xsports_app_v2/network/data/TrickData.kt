package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrickData(
    val data: MutableList<Trick>
)

data class Trick(
    val id: Int,
    val name: String,
    val description: String,
    val difficulty: String,
    val status: String ?= null
)