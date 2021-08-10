package com.technologies.mobileexercise.core.data.api

import com.technologies.mobileexercise.core.data.network.Resource
import com.technologies.mobileexercise.core.data.pojo.Transaction
import com.technologies.mobileexercise.core.data.pojo.User
import com.technologies.mobileexercise.feature.login.LoginForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

interface UserRepository {

    fun getAccounts(): Flow<Resource<List<User>>>
    fun getAccountTransactions(accountId: String): Flow<Resource<List<Transaction>>>

    class UserRepositoryImpl @Inject constructor(
        private val service: UserService
    ) : UserRepository {
        override fun getAccounts(): Flow<Resource<List<User>>> {
            return flow {
                emit(Resource.loading(data = null))
                try {
                    emit(
                        Resource.success(service.getAccounts())
                    )
                } catch (exception: Exception) {
                    emit(
                        Resource.error(
                            data = null, message = exception.message ?: "Error Occurred!"
                        )
                    )
                }

            }.flowOn(Dispatchers.IO).take(2)
        }

        override fun getAccountTransactions(accountId: String): Flow<Resource<List<Transaction>>> {
            return flow {
                emit(Resource.loading(data = null))
                try {
                    emit(
                        Resource.success(service.getAccountTransactions(accountId))
                    )
                } catch (exception: Exception) {
                    emit(
                        Resource.error(
                            data = null, message = exception.message ?: "Error Occurred!"
                        )
                    )
                }

            }.flowOn(Dispatchers.IO).take(2)
        }
    }
}