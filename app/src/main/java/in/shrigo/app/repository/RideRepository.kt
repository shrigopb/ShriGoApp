package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.models.Ride

class RideRepository {

    //gets Rides
    suspend fun getRides(): List<Ride> {
        return RetrofitClient
            .api
            .getRides()
    }

    //loginuser

    suspend fun loginUser(
        emailOrPhone: String,
        password: String
    ): LoginResponse? {

        return try {

            val response =
                RetrofitClient
                    .api
                    .loginUser(

                        LoginRequest(
                            emailOrPhone,
                            password
                        )
                    )

            Log.d(
                "LOGIN_API",
                "Code: ${response.code()}"
            )

            Log.d(
                "LOGIN_API",
                "Body: ${response.body()}"
            )

            if (response.isSuccessful) {

                response.body()

            } else {

                Log.d(
                    "LOGIN_API",
                    "Error: ${response.errorBody()?.string()}"
                )

                null
            }

        } catch (e: Exception) {

            Log.e(
                "LOGIN_API",
                "Exception",
                e
            )

            null
        }
    }
}