package com.kalagui.residify.ui.screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.internal.LinkedTreeMap
import com.kalagui.residify.navigation.NavigationEvent
import com.kalagui.residify.network.model.LoginResponse
import com.kalagui.residify.network.repository.LoginRepository
import com.kalagui.residify.store.AuthDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val authDataStore: AuthDataStore,
    ): ViewModel() {

    private val _form = MutableStateFlow(LoginState())
    val form: StateFlow<LoginState> = _form

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val gson = Gson()
//    private val _navigationEvents = Channel<NavigationEvent>(Channel.BUFFERED)
//    val navigationEvents = _navigationEvents.receiveAsFlow()

    fun setMail(mail: String){
        _errorMessage.value = null
        _form.update { current -> current.copy(mail = mail) }
    }
    fun setPassword(password: String){
        _errorMessage.value = null
        _form.update { current -> current.copy(password = password) }
    }

    fun login( onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                val params = form.value.toMap()
                val result = repository.login(params)
                _isLoading.value = false

                if (result.status == 200 || result.status == 201) {
                    _errorMessage.value = null
                    result.data?.let { data ->
                        try {
                            val jsonString = gson.toJson(data)
                            val loginResponse = gson.fromJson(jsonString, LoginResponse::class.java)

                            authDataStore.saveLoginData(
                                token = loginResponse.token ?: "",
                                username = loginResponse.user?.name ?: "",
                                userId = loginResponse.user?.id ?: -1
                            )
                            println("Login successful: ${loginResponse.user?.name}")
                            onSuccess()
                        }catch (e: JsonSyntaxException){
                            _errorMessage.value = "Error parsing login data: ${e.message}"
                        }catch (e: Exception){
                            _errorMessage.value = "Error processing login data: ${e.message}"
                        }
                    }

//                    navController.navigate("home")
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