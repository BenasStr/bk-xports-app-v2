package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CategoryData(
    val data: List<Category>
)

data class Category(
    val id: Int,
    val name: String,
    val photo: String ?= null,
    val sportId: Int
)