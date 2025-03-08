package com.kalagui.residify

import android.content.Context
import com.kalagui.residify.network.api.ApiClient
import com.kalagui.residify.network.repository.LoginRepository
import com.kalagui.residify.store.AuthDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun provideAuthDataStore(@ApplicationContext context: Context): AuthDataStore {
        return AuthDataStore(context)
    }

    @Provides
    @Singleton
    fun provideLoginRepository(apiClient: ApiClient): LoginRepository {
        return LoginRepository(apiClient)
    }
}