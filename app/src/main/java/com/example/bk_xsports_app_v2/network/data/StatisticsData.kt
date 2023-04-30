package com.example.bk_xsports_app_v2.network.data

import com.fasterxml.jackson.annotation.JsonFormat
import com.squareup.moshi.JsonClass
import java.util.*

@JsonClass(generateAdapter = true)
data class StatisticsData (
    val data: Statistics
)

data class Statistics(
    val learnedCount: Int,
    val learningCount: Int,
    val sports: List<SportStatistics>
)

data class SportStatistics(
    val name: String,
    val tricksCount: Int,
    val learnedCount: Int,
    val learningCount: Int,
    val sportCategories: List<CategoryStatistics>,
    val timeStamps: List<TimeStamps>
)

data class CategoryStatistics(
    val name: String,
    val learnedCount: Int,
    val learningCount: Int,
    val tricksCount: Int,
    val timeStamps: List<TimeStamps>
)

data class TimeStamps(
    val dateLearned: String,
    val countLearned: Int
)



