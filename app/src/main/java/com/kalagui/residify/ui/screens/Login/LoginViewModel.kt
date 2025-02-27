package com.kalagui.residify.ui.screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalagui.residify.network.repository.LoginRepository
import com.kalagui.residify.utils.toMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
    ): ViewModel() {

    private val _form = MutableStateFlow(LoginState())
    val form: StateFlow<LoginState> = _form

    fun setMail(mail: String){
        _form.update { current -> current.copy(mail = mail) }
    }
    fun setPassword(password: String){
        _form.update { current -> current.copy(password = password) }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            val params = form.toMap() ?: mapOf()
            val result = repository.login(params)
            println("respuesta servidor")
            println(result)
        }
    }
}