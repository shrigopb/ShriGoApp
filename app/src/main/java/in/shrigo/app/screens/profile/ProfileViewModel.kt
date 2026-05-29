package `in`.shrigo.app.screens.profile


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.ProfileResponse
import `in`.shrigo.app.repository.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel
    : ViewModel() {

    private val repository =

        ProfileRepository()

    private val _profile =

        MutableStateFlow<
                ProfileResponse?
                >(null)

    val profile:
            StateFlow<
                    ProfileResponse?
                    > = _profile

    private val _isLoading =

        MutableStateFlow(
            false
        )

    val isLoading:
            StateFlow<Boolean> =
        _isLoading

    private val _error =

        MutableStateFlow<
                String?
                >(null)

    val error:
            StateFlow<
                    String?
                    > = _error

    fun loadProfile(

        userId: Int,
        role: String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _error.value =
                null

            try {

                val response =

                    repository
                        .getProfile(

                            userId,
                            role
                        )

                if (
                    response !=
                    null
                ) {

                    _profile.value =
                        response

                } else {

                    _error.value =
                        "Profile not found"
                }

            } catch (
                e: Exception
            ) {

                _error.value =
                    e.message
            }

            _isLoading.value =
                false
        }
    }
}