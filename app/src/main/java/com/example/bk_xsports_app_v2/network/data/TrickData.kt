package com.example.bk_xsports_app_v2.network.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TrickData(
    val data: MutableList<TrickExtended>
)

@JsonClass(generateAdapter = true)
data class TrickMainData (
    val data: TrickExtended
)

data class Trick(
    val id: Int,
    val trickId: Int,
    val name: String,
    val shortDescription: String,
    val difficulty: String,
    val status: String ?= null
)

data class TrickExtended(
    val id: Int,
    val trickId: Int,
    val name: String,
    val shortDescription: String,
    val description: String,
    val difficulty: String,
    val status: String ?= null,
    val videoUrl: String ?= null,
    val trickParents: List<Trick>,
    val trickChildren: List<Trick>,
    val trickVariants: List<Trick>
)