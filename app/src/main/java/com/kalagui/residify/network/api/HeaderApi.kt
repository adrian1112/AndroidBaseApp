package com.kalagui.residify.network.api

import okhttp3.Interceptor
import okhttp3.Response

sealed class HeaderType(val value: String) {
    object AUTHENTICATED: HeaderType("authenticated")
    object GUEST: HeaderType("guest")
}

class HeaderInterceptor(private val headerParam: HeaderType) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headers = when (headerParam) {
            is HeaderType.AUTHENTICATED -> mapOf(
                "Authorization" to "Bearer user_token",
                "Content-Type" to "application/json",
                "Accept" to "application/json"

            )
            is HeaderType.GUEST -> mapOf(
                "Content-Type" to "application/json",
                "Accept" to "application/json"
            )
        }

        val newRequest = originalRequest.newBuilder().apply {
            for ((key, value) in headers) {
                addHeader(key, value)
            }
        }.build()

        return chain.proceed(newRequest)
    }
}