package com.kalagui.residify.network.model

data class LoginResponse(
    val token: String?,
    val user: User?
)

data class User(
    val id: Int?,
    val name: String?,
    val email: String?,
//    @SerializedName("user_image_url")
//    val userImageUrl: String? = null
)