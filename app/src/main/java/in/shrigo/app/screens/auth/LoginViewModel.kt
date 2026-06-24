package `in`.shrigo.app.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.ForgotPasswordRequest
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
    private val _forgotPasswordSuccess =
        MutableStateFlow(false)

    val forgotPasswordSuccess:
            StateFlow<Boolean>
            = _forgotPasswordSuccess

    private val _forgotPasswordMessage =
        MutableStateFlow<String?>(null)

    val forgotPasswordMessage:
            StateFlow<String?>
            = _forgotPasswordMessage
    //--------------------------------------
    //loginUser
    //--------------------------------------

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
                android.util.Log.d(
                    "LOGIN_DEBUG",
                    "Response = $response"
                )
                if (
                    response != null &&
                    response.success
                ) {

                    android.util.Log.d(
                        "LOGIN_DEBUG",
                        "SUCCESS = $response"
                    )

                    _loginState.value = response

                } else {

                    android.util.Log.e(
                        "LOGIN_DEBUG",
                        "FAILED = $response"
                    )

                    _error.value = "Invalid Login"
                }

            } catch (e: Exception) {

                _error.value =
                    e.message
            }

            _isLoading.value = false
        }
    }

//--------------------------------------
// forgotPassword
//--------------------------------------
fun forgotPassword(

    email: String

) {

    viewModelScope.launch {

        try {

            _isLoading.value =
                true

            android.util.Log.d(
                "FORGOT_PASSWORD",
                "Email = $email"
            )

            val response =

                repository
                    .forgotPassword(

                        ForgotPasswordRequest(
                            email
                        )
                    )

            android.util.Log.d(
                "FORGOT_PASSWORD",
                "Response = $response"
            )

            if (
                response?.success
                == true
            ) {

                _forgotPasswordSuccess
                    .value = true

                _forgotPasswordMessage
                    .value =
                    response.message

            } else {

                android.util.Log.e(
                    "FORGOT_PASSWORD",
                    "FAILED = ${response?.message}"
                )

                _forgotPasswordMessage
                    .value =

                    response?.message
                        ?: "Failed to send reset link"
            }

        } catch (
            e: Exception
        ) {

            android.util.Log.e(
                "FORGOT_PASSWORD",
                e.message ?: "Unknown Error"
            )

            _forgotPasswordMessage
                .value =
                e.message
        }

        _isLoading.value =
            false
    }
}
}