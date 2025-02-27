package com.kalagui.residify.network.api
import com.kalagui.residify.network.model.GenericResponse
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


enum class HttpMethod {
    GET, POST, PUT
}

class ApiClient() {
    private val baseUrl = "https://api.residify.bigproject.dev"
    private var headerType: HeaderType = HeaderType.GUEST
    private var client: OkHttpClient = createOkHttpClient(getHeaders())

    private val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun createOkHttpClient(headers: Map<String, String>): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder = original.newBuilder()

                headers.forEach { (key, value) ->
                    requestBuilder.addHeader(key, value)
                }

                chain.proceed(requestBuilder.build())
            }
            .build()
    }

    private fun getHeaders(): Map<String, String> {
        return when (headerType) {
            HeaderType.AUTHENTICATED -> mapOf(
                "Authorization" to "Bearer TOKEN_AQUI",
                "Content-Type" to "application/json",
                "Accept" to "application/json"
            )
            HeaderType.GUEST -> mapOf(
                "Content-Type" to "application/json",
                "Accept" to "application/json"
            )
        }
    }

    fun setHeaders(newHeaderType: HeaderType){
        headerType = newHeaderType
        client = createOkHttpClient(getHeaders())
    }

    suspend fun <T> makeRequest(
        method: HttpMethod,
        path: String,
        params: Map<String, Any>? = null
    ): GenericResponse<T> {
        return when (method) {
            HttpMethod.GET -> apiService.getRequest(path, params)
            HttpMethod.POST -> apiService.postRequest(path, params ?: emptyMap())
            HttpMethod.PUT -> apiService.putRequest(path, params ?: emptyMap())
        } as GenericResponse<T>
    }
}