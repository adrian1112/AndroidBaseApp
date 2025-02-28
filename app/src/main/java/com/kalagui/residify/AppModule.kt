package com.kalagui.residify

import com.kalagui.residify.network.api.ApiClient
import com.kalagui.residify.network.repository.LoginRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient = ApiClient()

    @Provides
    @Singleton
    fun provideLoginRepository(apiClient: ApiClient): LoginRepository {
        return LoginRepository(apiClient)
    }
}