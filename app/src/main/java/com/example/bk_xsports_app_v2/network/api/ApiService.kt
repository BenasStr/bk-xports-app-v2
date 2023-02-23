package com.example.bk_xsports_app_v2.network.api

import com.example.bk_xsports_app_v2.network.data.*
import com.example.bk_xsports_app_v2.network.request.LoginRequest
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private const val BASE_URL = "https://app-benasstr.cloud.okteto.net/api/"

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

    @GET("sports")
    suspend fun getSports(
        @Header("Authorization") authorization: String
    ) : SportData

    @GET("sports/{sportId}/categories")
    suspend fun getCategories(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int
    ) : CategoryData

    @GET("sports/{sportId}/categories/{categoryId}/tricks")
    suspend fun getTricks(
        @Header("Authorization") authorization: String,
        @Path("sportId") sportId: Int,
        @Path("categoryId") categoryId: Int
    ) : TrickData
}

object Api {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}