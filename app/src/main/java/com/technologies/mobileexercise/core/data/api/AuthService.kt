package com.technologies.mobileexercise.core.data.api

import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(retrofit: Retrofit) : AuthApi {
    private val api by lazy { retrofit.create(AuthApi::class.java) }

    override suspend fun login(username: String, password: String): ResponseBody =
        api.login(username, password)
}