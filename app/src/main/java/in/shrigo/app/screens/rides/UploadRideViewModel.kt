package `in`.shrigo.app.screens.rides

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.PlaceSuggestion
import `in`.shrigo.app.models.SignupRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.repository.PlacesRepository
import `in`.shrigo.app.repository.RideRepository

class UploadRideViewModel(
    application: Application
) : AndroidViewModel(application){

    private val repository =
        RideRepository()

    private val placesRepository =
        PlacesRepository(getApplication())
    private val _googleSuggestions =
        MutableStateFlow<List<PlaceSuggestion>>(emptyList())

    val googleSuggestions: StateFlow<List<PlaceSuggestion>>
            = _googleSuggestions

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

    private val _rideSource =
        MutableStateFlow("")

    val rideSource: StateFlow<String> = _rideSource

    fun onRideSourceChanged(value: String) {

        _rideSource.value = value

        searchPlaces(value)
    }
    fun searchPlaces(query: String) {

        Log.d("GOOGLE_PLACES", "searchPlaces() called with: $query")

        viewModelScope.launch {

            try {

                val results = placesRepository.searchPlaces(query)

                Log.d("GOOGLE_PLACES", "Found ${results.size} places")

                results.forEach {
                    Log.d(
                        "GOOGLE_PLACES",
                        "Primary = ${it.primaryText}"
                    )
                }

                _googleSuggestions.value = results

            } catch (e: Exception) {

                Log.e("GOOGLE_PLACES", "Exception", e)
            }
        }
    }
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

    fun updateRide(

        rideId: Int,

        request:
        UploadRideRequest

    ) {

        viewModelScope.launch {

            try {

                Log.d(
                    "UPDATE_RIDE",
                    "Button clicked"
                )

                _isLoading.value =
                    true

                val response =

                    RetrofitClient
                        .api
                        .updateRide(

                            rideId,

                            request
                        )

                Log.d(
                    "UPDATE_RIDE",
                    "Code = ${response.code()}"
                )

                Log.d(
                    "UPDATE_RIDE",
                    "Body = ${response.body()}"
                )

                if (

                    response.isSuccessful
                    &&
                    response.body()?.success == true

                ) {

                    _uploadSuccess.value =
                        true

                } else {

                    _error.value =

                        response.body()?.message
                            ?: "Update Failed"
                }

            } catch (
                e: Exception
            ) {

                Log.e(
                    "UPDATE_RIDE",
                    e.message
                        ?: "Unknown Error"
                )

                _error.value =
                    e.message
            }

            _isLoading.value =
                false
        }
    }

    //------------------------------------
    // Signup
    //--------------------------------------
    fun signupUser(

        request:
        SignupRequest

    ) {

        viewModelScope.launch {

            try {

                _isLoading.value =
                    true

                _error.value =
                    null

                Log.d(
                    "SIGNUP_REQUEST_User",
                    "Signup request started for ${request.userContact}"
                )
                Log.d(
                    "SIGNUP_REQUEST_Passenger",
                    "Signup request started for ${request.passengerContact}"
                )
                val response =

                    repository
                        .signupUser(
                            request
                        )

                Log.d(
                    "SIGNUP_RESPONSE",
                    response.toString()
                )

                if (
                    response?.success
                    == true
                ) {

                    Log.d(
                        "SIGNUP_SUCCESS",
                        response.message
                    )

                    _uploadSuccess
                        .value = true

                } else {

                    Log.e(
                        "SIGNUP_FAILED",
                        response?.message
                            ?: "NULL MESSAGE"
                    )

                    _error.value =

                        response?.message
                            ?: "Signup Failed"
                }

            } catch (
                e: Exception
            ) {

                Log.e(
                    "SIGNUP_EXCEPTION",
                    e.message
                        ?: "Unknown Error"
                )

                _error.value =

                    e.message
                        ?: "Network error"
            }

            _isLoading.value =
                false
        }
    }
}