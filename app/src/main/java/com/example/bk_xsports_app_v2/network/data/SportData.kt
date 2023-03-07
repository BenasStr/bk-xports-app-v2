package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SportData(
    val data: MutableList<Sport>
)

data class Sport(
    val id: Int,
    val name: String,
    val photo: String ?= null
)