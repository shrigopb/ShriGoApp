package `in`.shrigo.app.models

data class BookingResponse(

    val bookingId:
    Int,

    val rideId:
    String,

    val rideDate:
    String,

    val rideSource:
    String,

    val rideDesti:
    String,

    val rideVia:
    String?,

    val rideTime:
    String,

    val bookedSeats:
    String,

    val ridePrice:
    String,

    val driverContact:
    String,

    val driverUniqueId:
    String,

    val driverFirstName:
    String,

    val passengerFirstName:
    String,

    val passengerUniqueId:
    String,

    val passengerContact:
    String,

    val passengerEmail:
    String
)