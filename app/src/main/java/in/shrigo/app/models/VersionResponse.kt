package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class VersionResponse(

    @SerializedName("latestVersion")
    val latestVersion: String,

    @SerializedName("minimumVersion")
    val minimumVersion: String,

    @SerializedName("forceUpdate")
    val forceUpdate: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("playStoreUrl")
    val playStoreUrl: String
)