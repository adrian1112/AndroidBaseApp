package com.kalagui.residify.network.model

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.lang.reflect.Type

data class GenericResponse<T>(
    val status: Int?,
    val title: String?,
    val message: String?,
    val data: LinkedTreeMap<*, *>? = null
)
