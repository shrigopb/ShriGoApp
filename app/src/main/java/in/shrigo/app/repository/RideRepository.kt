package `in`.shrigo.app.repository

import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.Ride

class RideRepository {

    suspend fun getRides(): List<Ride> {
        return RetrofitClient
            .api
            .getRides()
    }
}