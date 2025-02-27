package com.kalagui.residify.network.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val token: String?,
    val user: User?
)

data class User(
    val id: String?,
    val name: String?,
    val email: String?,
    @SerializedName("user_image_url")
    val userImageUrl: String? = null
)