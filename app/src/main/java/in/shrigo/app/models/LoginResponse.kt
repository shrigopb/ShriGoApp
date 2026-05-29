package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @SerializedName("success")
    val success: Boolean = false,

    @SerializedName("loginType")
    val loginType: String? = null,

    @SerializedName("userId")
    val userId: Int? = null,

    @SerializedName("uniqueId")
    val uniqueId: String? = null,

    @SerializedName("firstName")
    val firstName: String? = null,

    @SerializedName("lastName")
    val lastName: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("role")
    val role: String? = null
)
