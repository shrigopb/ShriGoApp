package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.models.Ride
import `in`.shrigo.app.models.SignupRequest
import `in`.shrigo.app.models.SignupResponse
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
            Log.e( "UPLOAD_RIDE_ERROR", response.errorBody()?.string() ?: "No error body" )
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

    //--------------------------------
    //SignUp
    //---------------------------------
    suspend fun signupUser(

        request:
        SignupRequest

    ): SignupResponse? {

        return try {

            val response =

                RetrofitClient
                    .api
                    .signupUser(
                        request
                    )

            Log.d(
                "SIGNUP_CODE",
                "Code = ${response.code()}"
            )

            if (
                response.isSuccessful
            ) {

                val body =
                    response.body()

                Log.d(
                    "SIGNUP_SUCCESS",
                    "Body = $body"
                )

                body

            } else {

                val errorJson =

                    response
                        .errorBody()
                        ?.string()

                Log.e(
                    "SIGNUP_ERROR",
                    errorJson
                        ?: "No error body"
                )

                try {

                    com.google.gson.Gson()
                        .fromJson(

                            errorJson,

                            SignupResponse::class.java
                        )

                } catch (
                    e: Exception
                ) {

                    SignupResponse(

                        success = false,

                        message =
                            "Signup failed"
                    )
                }
            }

        } catch (
            e: Exception
        ) {

            Log.e(
                "SIGNUP_EXCEPTION",
                e.message
                    ?: "Unknown Error"
            )

            SignupResponse(

                success = false,

                message =
                    e.message
                        ?: "Network error"
            )
        }
    }
}
