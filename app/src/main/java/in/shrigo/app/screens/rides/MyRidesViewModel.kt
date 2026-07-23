package `in`.shrigo.app.screens.rides

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.MyRideResponse
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.repository.MyRidesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import `in`.shrigo.app.repository.FavoriteRouteRepository
import `in`.shrigo.app.models.SaveFavoriteRequest



class MyRidesViewModel
    : ViewModel() {


      private val repository =  MyRidesRepository()
    private val favoriteRepository = FavoriteRouteRepository()

    private val _favoriteSaved = MutableStateFlow(false)
    val favoriteSaved: StateFlow<Boolean> = _favoriteSaved

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
    private val _deleteSuccess =

        MutableStateFlow(
            false
        )

    val deleteSuccess:
            StateFlow<Boolean> =
        _deleteSuccess
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

    )

    {

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

                _deleteSuccess.value =
                    true

                loadMyRides(
                    uniqueId
                )
            }
        }
    }

    fun clearDeleteSuccess() {

        _deleteSuccess.value =
            false
    }

    //-------------------------
    //Update Ride
    //-------------------------
    fun updateRide(

        rideId: Int,

        request: UploadRideRequest,

        uniqueId: String

    ) {

        viewModelScope.launch {

            val success =

                repository
                    .updateRide(

                        rideId,

                        request
                    )

            if (success) {

                loadMyRides(
                    uniqueId
                )
            }
        }
    }

    //------------------------
    //
    //------------------------
    fun saveFavorite(
        ride: MyRideResponse
    ) {

        viewModelScope.launch {

            try {

                val request = SaveFavoriteRequest(

                    driverUniqueId = ride.driverUniqueId ?: "",

                    favoriteName = "${ride.rideSource} → ${ride.rideDesti}",

                    routeName = "${ride.rideSource} → ${ride.rideDesti}",

                    rideFrom = ride.rideSource ?: "",

                    rideVia = ride.rideVia ?: "",

                    rideTo = ride.rideDesti ?: "",

                    rideTime = ride.rideTime ?: "",

                    ridePrice = ride.ridePrice?.toDoubleOrNull() ?: 0.0,

                    rideSeats = ride.rideSeats?.toIntOrNull() ?: 0
                )

                val response = favoriteRepository.saveFavorite(request)

                if (response.isSuccessful) {

                    Log.d("FAVORITE_ROUTE", "Saved")

                } else {

                    Log.e("FAVORITE_ROUTE", "HTTP ${response.code()}")

                    Log.e(
                        "FAVORITE_ROUTE",
                        response.errorBody()?.string() ?: "No error body"
                    )
                }

            } catch (e: Exception) {

                Log.e(
                    "FAVORITE_ROUTE",
                    "Exception : ${e.message}",
                    e
                )
            }
        }


    }


    fun clearFavoriteSaved() {
        _favoriteSaved.value = false
    }
}