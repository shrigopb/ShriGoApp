package `in`.shrigo.app.api

import `in`.shrigo.app.models.Ride
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse

interface ApiService {

    @GET("api/RideApi")
    suspend fun getRides(): List<Ride>


    @POST("api/LoginApi")
    suspend fun loginUser(

        @Body request: LoginRequest

    ): Response<LoginResponse>
}