package `in`.shrigo.app.models

data class NotificationResponse(

    val notificationId: Int,

    val userUniqueId: String?,

    val title: String?,

    val message: String?,

    val notificationType: String?,

    val isRead: Boolean,

    val createdDate: String?
)