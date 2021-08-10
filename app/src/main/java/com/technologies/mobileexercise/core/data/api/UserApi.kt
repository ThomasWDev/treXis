package com.technologies.mobileexercise.core.data.api

import com.technologies.mobileexercise.core.data.pojo.Transaction
import com.technologies.mobileexercise.core.data.pojo.User
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserApi {

    @GET("accounts")
    suspend fun getAccounts(): List<User>

    @GET("transactions")
    suspend fun getAccountTransactions(@Query("accountId") accountId: String): List<Transaction>
}