package com.kalagui.residify.ui.screens.Login

data class LoginState (
    val mail: String = "dev.oscar.romnero@outlook.coma",
    val password: String = "PassClient123*",
) {
    fun toMap(): Map<String, String> = mapOf(
        "email" to mail,
        "password" to password
    )
}

