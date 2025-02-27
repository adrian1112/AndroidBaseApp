package com.kalagui.residify.utils
import com.google.gson.Gson


fun <T : Any> T.toMap(): Map<String, Any> {
    return try {
        val gson = Gson()
        val userJson = gson.toJson(this)
        gson.fromJson(userJson, Map::class.java) as Map<String, Any>
    } catch (e: Exception) {
        emptyMap()
    }
}
