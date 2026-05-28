package `in`.shrigo.app.repository

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

            if (response.isSuccessful) {

                response.body()

            } else {

                null
            }

        } catch (e: Exception) {

            null
        }
    }
}