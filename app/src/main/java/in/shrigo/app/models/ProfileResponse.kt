package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class ProfileResponse(

    @SerializedName("userId")
    val userId: Int? = null,

    @SerializedName("uniqueId")
    val uniqueId: String? = null,

    @SerializedName("firstName")
    val firstName: String? = null,

    @SerializedName("lastName")
    val lastName: String? = null,

    @SerializedName("age")
    val age: String? = null,

    @SerializedName("email")
    val email: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("role")
    val role: String? = null,

    @SerializedName("imagePath")
    val imagePath: String? = null,

    @SerializedName("vehicleModel")
    val vehicleModel: String? = null,

    @SerializedName("vehicleRegNo")
    val vehicleRegNo: String? = null,

    @SerializedName("vehicleInsur")
    val vehicleInsur: String? = null,

    @SerializedName("subscription")
    val subscription: String? = null
)