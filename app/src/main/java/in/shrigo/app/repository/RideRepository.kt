package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.models.Ride
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.models.UploadRideResponse

class RideRepository {

    //--------------------------------------
    // GET RIDES
    //--------------------------------------

    suspend fun getRides():
            List<Ride> {

        return RetrofitClient
            .api
            .getRides()
    }

    //--------------------------------------
    // LOGIN USER
    //--------------------------------------

    suspend fun loginUser(

        emailOrPhone:
        String,

        password:
        String

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

            if (
                response.isSuccessful
            ) {

                response.body()

            } else {

                Log.d(

                    "LOGIN_API",

                    response
                        .errorBody()
                        ?.string()
                        ?: "Unknown Error"
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

    //--------------------------------------
    // UPLOAD RIDE
    //--------------------------------------

    suspend fun uploadRide(

        request:
        UploadRideRequest

    ): UploadRideResponse? {

        return try {

            val response =

                RetrofitClient
                    .api
                    .uploadRide(
                        request
                    )

            Log.d(

                "UPLOAD_RIDE",

                "Code: ${response.code()}"
            )

            Log.d(

                "UPLOAD_RIDE",

                "Body: ${response.body()}"
            )

            if (
                response.isSuccessful
            ) {

                response.body()

            } else {

                Log.e(

                    "UPLOAD_RIDE",

                    response
                        .errorBody()
                        ?.string()
                        ?: "Unknown Error"
                )

                null
            }

        } catch (e: Exception) {

            Log.e(

                "UPLOAD_RIDE",

                "Exception",

                e
            )

            null
        }
    }
}
