package com.kalagui.residify.ui.screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kalagui.residify.network.repository.LoginRepository
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

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun setMail(mail: String){
        _errorMessage.value = null
        _form.update { current -> current.copy(mail = mail) }
    }
    fun setPassword(password: String){
        _errorMessage.value = null
        _form.update { current -> current.copy(password = password) }
    }

    fun login() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val params = form.value.toMap()
                val result = repository.login(params)
                _isLoading.value = false

                if (result.status == 200 || result.status == 201) {
                    _errorMessage.value = null
                    // Navigate to next screen or show success
                } else {
                    _errorMessage.value = result.message ?: "Login failed"
                }

            } catch (e: retrofit2.HttpException) {
                _isLoading.value = false
                _errorMessage.value = when (e.code()) {
//                    401 -> "Invalid email or password"
//                    404 -> "Service not found"
                    else -> "HTTP ${e.code()}: ${e.message()}"
                }
            } catch (e: Exception) {
                _isLoading.value = false
                _errorMessage.value = "Network error: ${e.message}"
//                println("General Error: ${e.message}")
            }

        }
    }
}