package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.NotificationResponse

class NotificationRepository {

    suspend fun getNotifications(
        uniqueId: String
    ): List<NotificationResponse> {

        return try {

            RetrofitClient
                .api
                .getNotifications(uniqueId)

        } catch (e: Exception) {

            emptyList()

        }
    }
}