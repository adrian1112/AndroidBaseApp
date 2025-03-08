package com.kalagui.residify.network.repository

import com.google.gson.internal.LinkedTreeMap
import com.kalagui.residify.network.api.ApiClient
import com.kalagui.residify.network.api.HttpMethod
import com.kalagui.residify.network.model.GenericResponse
import com.kalagui.residify.network.model.LoginResponse
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiClient: ApiClient){

    suspend fun login(params: Map<String, String>): GenericResponse<LinkedTreeMap<*, *>> {
        return apiClient.makeRequest<LinkedTreeMap<*, *>>(
            method = HttpMethod.POST,
            path = "api/v1/user/auth/login",
            params = params
        )
    }
}