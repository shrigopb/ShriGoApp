package `in`.shrigo.app.repository

import `in`.shrigo.app.api.ApiService
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.FavoriteRoute
import `in`.shrigo.app.models.SaveFavoriteRequest

class FavoriteRouteRepository {

    suspend fun saveFavorite(
        request: SaveFavoriteRequest
    ) =
        RetrofitClient.api.saveFavorite(request)

    suspend fun getFavorites(
        driverUniqueId: String
    ) =
        RetrofitClient.api.getFavorites(driverUniqueId)

    suspend fun deleteFavorite(
        id: Int
    ) =
        RetrofitClient.api.deleteFavorite(id)

    suspend fun updateFavorite(
        id: Int,
        request: FavoriteRoute
    ) =
        RetrofitClient.api.updateFavorite(id, request)
}