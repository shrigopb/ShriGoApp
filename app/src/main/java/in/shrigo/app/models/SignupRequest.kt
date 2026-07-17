package `in`.shrigo.app.models

import com.google.gson.annotations.SerializedName

data class SignupRequest(

    //----------------------------------
    // Passenger Fields
    //----------------------------------

    @SerializedName("PassengerFirstName")
    val passengerFirstName: String? = null,

    @SerializedName("PassengerLastName")
    val passengerLastName: String? = null,

    @SerializedName("PassengerAge")
    val passengerAge: String? = null,

    @SerializedName("PassengerEmail")
    val passengerEmail: String? = null,

    @SerializedName("PassengerContact")
    val passengerContact: String? = null,

    @SerializedName("PassengerPswd")
    val passengerPswd: String? = null,

    //----------------------------------
    // Driver Fields
    //----------------------------------

    //----------------------------------
// Driver Fields
//----------------------------------

    @SerializedName("DriverFirstName")
    val userFirstName: String? = null,

    @SerializedName("DriverLastName")
    val userLastName: String? = null,

    @SerializedName("DriverAge")
    val userAge: String? = null,

    @SerializedName("DriverEmail")
    val userEmail: String? = null,

    @SerializedName("DriverContact")
    val userContact: String? = null,

    @SerializedName("DriverPswd")
    val userPswd: String? = null,

    @SerializedName("DriverRole")
    val driverRole: String? = null,
    //----------------------------------
    // Shared
    //----------------------------------

    @SerializedName("Role")
    val passengerRole: String? = null,

    @SerializedName("UserRole")
    val userRole: String? = null,

    @SerializedName("Subscription")
    val subscription: String? = null,

    @SerializedName("VehicleRegNo")
    val vehicleRegNo: String? = null,

    @SerializedName("VehicleInsur")
    val vehicleInsur: String? = null,

    @SerializedName("VehicleModel")
    val vehicleModel: String? = null,

    @SerializedName("AcceptedTerms")
    val acceptedTerms: Boolean = false
)