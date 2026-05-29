package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class LoginRequest(

    @SerializedName("emailOrPhone")
    val emailOrPhone: String,

    @SerializedName("password")
    val password: String
)