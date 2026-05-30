package `in`.shrigo.app.api

import `in`.shrigo.app.models.Ride
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.models.ProfileResponse
import retrofit2.http.Path

interface ApiService {
    //--------------------------------------
    //Get Methods
    //--------------------------------------
    //Ride Info API
    @GET("api/RideApi/active")
    suspend fun getRides(): List<Ride>
    //Profile Info API
    @GET(
        "api/ProfileApi/{userId}/{role}"
    )

    suspend fun getProfile(

        @Path("userId")
        userId: Int,

        @Path("role")
        role: String

    ): Response<ProfileResponse>


    //--------------------------------------
    //Post Methods
    //--------------------------------------

    //Login Info API
    @POST("api/LoginApi")
    suspend fun loginUser(

        @Body request: LoginRequest

    ): Response<LoginResponse>
}