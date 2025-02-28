package com.kalagui.residify.network.api

import com.kalagui.residify.network.model.GenericResponse
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ApiService {
    @GET("{path}") // Llamada GET dinámica
    suspend fun <T>getRequest(
        @Path("path", encoded = true) path: String,
        @QueryMap params: Map<String, String>? = null
    ): GenericResponse<T>

    @POST("{path}") // Llamada POST dinámica
    @FormUrlEncoded
    suspend fun <T> postRequest(
        @Path("path", encoded = true) path: String,
        @FieldMap params: Map<String, String>
    ): GenericResponse<T>

    @PUT("{path}") // Llamada POST dinámica
    @FormUrlEncoded
    suspend fun <T> putRequest(
        @Path("path", encoded = true) path: String,
        @FieldMap params: Map<String, String>
    ): GenericResponse<T>
}