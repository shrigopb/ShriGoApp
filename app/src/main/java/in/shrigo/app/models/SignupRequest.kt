package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    @SerializedName(
        "PassengerFirstName"
    )
    val passengerFirstName:
    String,

    @SerializedName(
        "PassengerLastName"
    )
    val passengerLastName:
    String,

    @SerializedName(
        "PassengerAge"
    )
    val passengerAge:
    String,

    @SerializedName(
        "PassengerEmail"
    )
    val passengerEmail:
    String,

    @SerializedName(
        "PassengerContact"
    )
    val passengerContact:
    String,

    @SerializedName(
        "PassengerPswd"
    )
    val passengerPswd:
    String
)