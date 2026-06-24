package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class NotificationCountResponse(

    @SerializedName("count")
    val count: Int = 0
)