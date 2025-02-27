package com.kalagui.residify.network.repository

import com.kalagui.residify.network.api.ApiClient
import com.kalagui.residify.network.api.HttpMethod
import com.kalagui.residify.network.model.GenericResponse
import com.kalagui.residify.network.model.LoginResponse

class LoginRepository(private val apiClient: ApiClient) {

    suspend fun login(params: Map<String, Any>): GenericResponse<LoginResponse> {
        return apiClient.makeRequest(
            method = HttpMethod.POST,
            path = "/api/v1/user/resident/auth/login",
            params = params
        )
    }
}