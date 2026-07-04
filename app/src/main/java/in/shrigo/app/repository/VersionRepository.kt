package `in`.shrigo.app.repository

import `in`.shrigo.app.api.ApiService
import `in`.shrigo.app.models.VersionResponse

class VersionRepository(
    private val apiService: ApiService
) {

    suspend fun getLatestVersion(): VersionResponse {
        return apiService.getLatestVersion()
    }
}