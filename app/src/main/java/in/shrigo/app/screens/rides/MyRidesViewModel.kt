package `in`.shrigo.app.screens.rides

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.MyRideResponse
import `in`.shrigo.app.repository.MyRidesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyRidesViewModel
    : ViewModel() {

    private val repository =

        MyRidesRepository()

    //-----------------------------------
    // Rides
    //-----------------------------------

    private val _rides =

        MutableStateFlow<
                List<MyRideResponse>
                >(emptyList())

    val rides:
            StateFlow<
                    List<MyRideResponse>
                    > = _rides

    //-----------------------------------
    // Loading
    //-----------------------------------

    private val _isLoading =

        MutableStateFlow(
            false
        )

    val isLoading:
            StateFlow<Boolean> =
        _isLoading

    //-----------------------------------
    // Error
    //-----------------------------------

    private val _error =

        MutableStateFlow<
                String?
                >(null)

    val error:
            StateFlow<
                    String?
                    > = _error

    //-----------------------------------
    // Load My Rides
    //-----------------------------------

    fun loadMyRides(

        uniqueId:
        String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _error.value =
                null

            try {

                val response =

                    repository
                        .getMyRides(
                            uniqueId
                        )

                _rides.value =
                    response

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

    //------------------------
    //Delete selected Ride
    //--------------------------
    fun deleteRide(

        rideId: Int,
        uniqueId: String

    ) {

        viewModelScope.launch {

            Log.d(
                "DELETE_RIDE",
                "RideId = $rideId"
            )

            val success =

                repository
                    .deleteRide(
                        rideId
                    )

            Log.d(
                "DELETE_RIDE",
                "Success = $success"
            )

            if (
                success
            ) {

                loadMyRides(
                    uniqueId
                )
            }
        }
    }
}