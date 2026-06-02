package `in`.shrigo.app.repository

import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.MyRideResponse
import android.util.Log
import `in`.shrigo.app.models.UploadRideRequest

class MyRidesRepository {



    suspend fun getMyRides(
        uniqueId: String
    ): List<MyRideResponse> {

        return try {

            val response =

                RetrofitClient
                    .api
                    .getMyRides(
                        uniqueId
                    )

            Log.d(
                "MY_RIDES_API",
                "Code = ${response.code()}"
            )

            Log.d(
                "MY_RIDES_API",
                "Body = ${response.body()}"
            )

            if (
                response.isSuccessful
            ) {

                response.body()
                    ?: emptyList()

            } else {

                emptyList()
            }

        } catch (
            e: Exception
        ) {

            Log.e(
                "MY_RIDES_API",
                e.message ?: ""
            )

            emptyList()
        }
    }
    //---------------------------
    //Delete selected rides
    //---------------------------
    suspend fun deleteRide(

        rideId: Int

    ): Boolean {

        return try {

            val response =

                RetrofitClient
                    .api
                    .deleteRide(
                        rideId
                    )

            Log.d(
                "DELETE_API",
                "Code = ${response.code()}"
            )

            Log.d(
                "DELETE_API",
                "Success = ${response.isSuccessful}"
            )

            response
                .isSuccessful

        } catch (
            e: Exception
        ) {

            Log.e(
                "DELETE_API",
                e.message
                    ?: "Error"
            )

            false
        }
    }
    //-------------------
    //Update Ride
    //---------------------
    suspend fun updateRide(

        rideId: Int,

        request: UploadRideRequest

    ): Boolean {

        val response =

            RetrofitClient
                .api
                .updateRide(

                    rideId,

                    request
                )

        return response.isSuccessful
    }
}