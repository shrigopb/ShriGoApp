package `in`.shrigo.app.screens.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.NotificationResponse
import `in`.shrigo.app.repository.NotificationRepository
import kotlinx.coroutines.launch

class NotificationViewModel : ViewModel() {

    private val repository =
        NotificationRepository()

    val notifications =
        mutableStateListOf<NotificationResponse>()

    fun loadNotifications(
        uniqueId: String
    ) {

        viewModelScope.launch {

            try {

                val result =
                    repository.getNotifications(
                        uniqueId
                    )
                Log.d(
                    "NOTIFICATION_API",
                    result.toString()
                )
                notifications.clear()

                notifications.addAll(
                    result
                )

            } catch (e: Exception) {

                e.printStackTrace()
            }
        }
    }
}