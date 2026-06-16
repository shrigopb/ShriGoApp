package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class BookRideRequest(

    @SerializedName("RideId")
    val rideId: Int,

    @SerializedName("BookedSeats")
    val bookedSeats: Int,

    @SerializedName(
        "PassengerFirstName"
    )
    val passengerFirstName:
    String,

    @SerializedName(
        "PassengerUniqueId"
    )
    val passengerUniqueId:
    String,

    @SerializedName(
        "PassengerContact"
    )
    val passengerContact:
    String,

    @SerializedName(
        "PassengerEmail"
    )
    val passengerEmail:
    String
)