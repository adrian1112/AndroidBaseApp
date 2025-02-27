package com.kalagui.residify.ui.screens.Register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class RegistrationViewModel: ViewModel() {
    private val _form = MutableStateFlow(RegistrationState())
    val form: StateFlow<RegistrationState> = _form

    fun setName(name: String){
        _form.update { current -> current.copy(name = name) }
    }
    fun setMail(mail: String){
        _form.update { current -> current.copy(mail = mail) }
    }
    fun setPassword(password: String){
        _form.update { current -> current.copy(password = password) }
    }
    fun setConfirmPassword(password: String){
        _form.update { current -> current.copy(confirmPassword = password) }
    }

    fun registerUser(){

    }
}