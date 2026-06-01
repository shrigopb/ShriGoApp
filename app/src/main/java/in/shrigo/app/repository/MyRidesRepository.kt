package `in`.shrigo.app.repository

import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.MyRideResponse
import android.util.Log
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
}