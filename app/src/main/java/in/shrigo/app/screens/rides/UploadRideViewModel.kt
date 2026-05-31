package `in`.shrigo.app.screens.rides

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.repository.RideRepository

class UploadRideViewModel : ViewModel() {

    private val repository =
        RideRepository()

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading:
            StateFlow<Boolean>
            = _isLoading

    private val _uploadSuccess =
        MutableStateFlow(false)

    val uploadSuccess:
            StateFlow<Boolean>
            = _uploadSuccess

    private val _error =
        MutableStateFlow<String?>(null)

    val error:
            StateFlow<String?>
            = _error

    fun uploadRide(

        request:
        UploadRideRequest

    ) {

        viewModelScope.launch {

            try {

                _isLoading.value =
                    true

                val response =

                    repository
                        .uploadRide(
                            request
                        )

                if (
                    response?.success
                    == true
                ) {

                    _uploadSuccess
                        .value = true

                } else {

                    _error.value =

                        response?.message
                            ?: "Upload failed"
                }

            } catch (e: Exception) {

                _error.value =
                    e.message

            } finally {

                _isLoading.value =
                    false
            }
        }
    }
}