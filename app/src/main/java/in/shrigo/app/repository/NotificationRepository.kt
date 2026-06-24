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
    //------------------------------
    // Notification Count
    //------------------------------
    suspend fun getNotificationCount(
        uniqueId: String
    ): Int {

        return try {

            val response =
                RetrofitClient.api
                    .getNotificationCount(
                        uniqueId
                    )

            if (
                response.isSuccessful
            ) {
                response.body()?.count ?: 0
            }
            else {
                0
            }

        } catch (e: Exception) {

            0
        }
    }
}