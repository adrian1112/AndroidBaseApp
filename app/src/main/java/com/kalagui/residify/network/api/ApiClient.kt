package com.kalagui.residify.network.api
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kalagui.residify.network.model.GenericResponse
//import com.kalagui.residify.network.model.GenericResponseTypeAdapter
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


enum class HttpMethod {
    GET, POST, PUT
}

class ApiClient @Inject constructor() {
    private val baseUrl = "https://api.bibalance.bigproject.dev"
    private var headerType: HeaderType = HeaderType.GUEST
    private var client: OkHttpClient = createOkHttpClient(getHeaders())

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun createOkHttpClient(headers: Map<String, String>): OkHttpClient {
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor { chain ->
                val original: Request = chain.request()
                val requestBuilder = original.newBuilder()
                headers.forEach { (key, value) ->
                    requestBuilder.addHeader(key, value)
                }

                val response = chain.proceed(requestBuilder.build())
                if (response.code in 400..599) {
                    val body = response.body
                    if (body != null && response.header("Content-Type")?.contains("application/json") == true) {
                        val jsonString = body.string()
                        response.newBuilder()
                            .code(200) // Fake success for Retrofit
                            .body(jsonString.toResponseBody(body.contentType()))
                            .build()
                    } else {
                        response
                    }
                } else {
                    response
                }
            }.build()

    }

    private fun getHeaders(): Map<String, String> {
        return when (headerType) {
            HeaderType.AUTHENTICATED -> mapOf(
                "Authorization" to "Bearer TOKEN_AQUI",
//                "Content-Type" to "application/json",
                "Accept" to "application/json"
            )
            HeaderType.GUEST -> mapOf(
//                "Content-Type" to "application/json",
                "Accept" to "application/json"
            )
        }
    }

    fun setHeaders(newHeaderType: HeaderType){
        headerType = newHeaderType
        client = createOkHttpClient(getHeaders())
    }

    suspend inline fun <reified T > makeRequest(
        method: HttpMethod,
        path: String,
        params: Map<String, String>? = emptyMap()
    ): GenericResponse<T> {
        return when (method) {
            HttpMethod.GET -> apiService.getRequest(path, params)
            HttpMethod.POST -> apiService.postRequest(path, params ?: emptyMap())
            HttpMethod.PUT -> apiService.putRequest(path, params ?: emptyMap())
        }
    }
}