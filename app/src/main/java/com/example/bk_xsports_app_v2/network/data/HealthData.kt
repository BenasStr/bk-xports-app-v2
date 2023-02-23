package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class HealthData(
    val health: String
)