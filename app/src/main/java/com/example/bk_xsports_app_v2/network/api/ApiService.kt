package com.example.bk_xsports_app_v2.network.api

import com.example.bk_xsports_app_v2.network.data.*
import com.example.bk_xsports_app_v2.network.request.LoginRequest
import com.example.bk_xsports_app_v2.network.request.RegisterRequest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


//private const val BASE_URL = "https://app-benasstr.cloud.okteto.net/api/v1/"
private const val BASE_URL = "http://192.168.1.219:8080/api/v1/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface ApiService {
    @GET("health")
    suspend fun getHealth() : HealthData

    @POST("auth/login")
    suspend fun getToken(
        @Body requestBody: LoginRequest
    ) : TokenData

    @POST("auth/register")
    suspend fun registerUser(
        @Body requestBody: RegisterRequest
    ) : TokenData

    @GET("sports/{sportId}/categories")
    suspend fun getCategories(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int
    ) : CategoryData

    @GET("sports/{sportId}/categories/{categoryId}/tricks?extended=true")
    suspend fun getTricks(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int,
        @Path("categoryId") categoryId: Int
    ) : TrickData

    @GET("sports/{sportId}/categories/{categoryId}/tricks?extended=true")
    suspend fun getTricksWithSearch(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int,
        @Path("categoryId") categoryId: Int,
        @Query("search") search: String
    ) : TrickData

    @GET("sports/{sportId}/categories/{categoryId}/tricks/{trickId}")
    suspend fun getTrick(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int,
        @Path("categoryId") categoryId: Int,
        @Path("trickId") trickId: Int
    ) : TrickMainData

    @GET("sports/my_list")
    suspend fun getMyListTricks(
        @Header("Authorization") authorization: String
    ) : SportData

    @GET("sports/my_list/explore")
    suspend fun getSportsExplore(
        @Header("Authorization") authorization: String
    ) : SportData

    @POST("sports/my_list")
    suspend fun addMyListSport(
        @Header("Authorization") authorization: String,
        @Query("sportId") sportId: Int
    )

    @PUT("sports/{sportId}/categories/{categoryId}/tricks/{trickId}/progress")
    suspend fun updateProgress(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int,
        @Path("categoryId") categoryId: Int,
        @Path("trickId") trickId: Int
    ) : TrickMainData

    @DELETE("sports/my_list")
    suspend fun deleteMyListSport(
        @Header("Authorization") authorization: String,
        @Query("sportId") sportId: Int
    )

    @GET("statistics")
    suspend fun getStatistics(
        @Header("Authorization") authorization: String
    ) : StatisticsData
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}