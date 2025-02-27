package com.kalagui.residify.network.api

import com.kalagui.residify.network.model.GenericResponse
import retrofit2.http.*

interface ApiService {
    @GET("{path}") // Llamada GET dinámica
    suspend fun getRequest(
        @Path("path") path: String,
        @QueryMap params: Map<String, Any>? = null
    ): GenericResponse<Any>

    @POST("{path}") // Llamada POST dinámica
    @FormUrlEncoded
    suspend fun postRequest(
        @Path("path") path: String,
        @FieldMap params: Map<String, Any>
    ): GenericResponse<Any>

    @PUT("{path}") // Llamada POST dinámica
    @FormUrlEncoded
    suspend fun putRequest(
        @Path("path") path: String,
        @FieldMap params: Map<String, Any>
    ): GenericResponse<Any>
}