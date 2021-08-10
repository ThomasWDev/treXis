package com.technologies.mobileexercise.core.data.api

import com.technologies.mobileexercise.core.data.pojo.Transaction
import com.technologies.mobileexercise.core.data.pojo.User
import okhttp3.ResponseBody
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class UserService @Inject constructor(retrofit: Retrofit) : UserApi {
    private val api by lazy { retrofit.create(UserApi::class.java) }

    override suspend fun getAccounts(): List<User> = api.getAccounts()
    override suspend fun getAccountTransactions(accountId: String): List<Transaction> =
        api.getAccountTransactions(accountId)
}