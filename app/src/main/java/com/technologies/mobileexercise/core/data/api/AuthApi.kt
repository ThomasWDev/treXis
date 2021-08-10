package com.technologies.mobileexercise.core.data.api

import okhttp3.ResponseBody
import retrofit2.http.*

interface AuthApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(@Field("username") username: String, @Field("password") password: String): ResponseBody
}