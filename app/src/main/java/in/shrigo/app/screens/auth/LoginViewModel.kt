package `in`.shrigo.app.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.repository.RideRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val repository =
        RideRepository()

    private val _loginState =
        MutableStateFlow<LoginResponse?>(null)

    val loginState:
            StateFlow<LoginResponse?> =
        _loginState

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading:
            StateFlow<Boolean> =
        _isLoading

    private val _error =
        MutableStateFlow<String?>(null)

    val error:
            StateFlow<String?> =
        _error

    fun loginUser(
        emailOrPhone: String,
        password: String
    ) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                val response =
                    repository.loginUser(
                        emailOrPhone,
                        password
                    )

                if (
                    response != null
                    &&
                    response.success
                ) {

                    _loginState.value =
                        response

                } else {

                    _error.value =
                        "Invalid Login"
                }

            } catch (e: Exception) {

                _error.value =
                    e.message
            }

            _isLoading.value = false
        }
    }
}