
package com.technologies.mobileexercise.core.data.api

import com.technologies.mobileexercise.core.data.network.Resource
import com.technologies.mobileexercise.feature.login.LoginForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import okhttp3.ResponseBody
import java.lang.Exception
import javax.inject.Inject

interface AuthRepository {

    fun login(form: LoginForm): Flow<Resource<ResponseBody>>

    class AuthRepositoryImpl @Inject constructor(
        private val service: AuthService
    ) : AuthRepository {
        override fun login(form: LoginForm): Flow<Resource<ResponseBody>> {
            return flow {
                emit(Resource.loading(data = null))
                try {
                    emit(Resource.success(service.login(form.username!!, form.password!!)))
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