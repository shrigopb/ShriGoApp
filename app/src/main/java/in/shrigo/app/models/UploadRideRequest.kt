package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class UploadRideRequest(

    @SerializedName("RideDate")
    val rideDate: String,

    @SerializedName("RideSource")
    val rideSource: String,

    @SerializedName("RideDesti")
    val rideDesti: String,

    @SerializedName("RideVia")
    val rideVia: String?,

    @SerializedName("RideTime")
    val rideTime: String,

    @SerializedName("RideSeats")
    val rideSeats: String,

    @SerializedName("RidePrice")
    val ridePrice: String,

    @SerializedName("DriverContact")
    val driverContact: String,

    @SerializedName("DriverUniqueId")
    val driverUniqueId: String,

    @SerializedName("DriverFirstName")
    val driverFirstName: String
)

