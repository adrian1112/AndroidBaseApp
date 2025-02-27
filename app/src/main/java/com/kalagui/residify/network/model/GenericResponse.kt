package com.kalagui.residify.network.model

data class GenericResponse<T>(
    val status: Int?,
    val title: String?,
    val message: String?,
    val data: T? = null
)