package com.kalagui.residify.ui.screens.Register

data class RegistrationState (
    val name: String = "",
    val mail: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val validPassword: Boolean = false,
    val validConfirmPassword: Boolean = false,
    val validForm: Boolean = false,
    val showError: Boolean = false,
    val message: String = ""
)

