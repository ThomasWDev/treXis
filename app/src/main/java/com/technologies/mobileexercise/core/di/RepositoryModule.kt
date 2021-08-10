package com.technologies.mobileexercise.core.di

import com.technologies.mobileexercise.core.data.api.AuthRepository
import com.technologies.mobileexercise.core.data.api.AuthService
import com.technologies.mobileexercise.core.data.api.UserRepository
import com.technologies.mobileexercise.core.data.api.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideAuthRepository(
        service: AuthService
    ): AuthRepository {
        return AuthRepository.AuthRepositoryImpl(service)
    }


    @Provides
    @ViewModelScoped
    fun provideUserRepository(
        service: UserService
    ): UserRepository {
        return UserRepository.UserRepositoryImpl(service)
    }

}