package `in`.shrigo.app.api

import `in`.shrigo.app.models.Ride
import retrofit2.http.GET

interface ApiService {

    @GET("api/RideApi")
    suspend fun getRides(): List<Ride>
}