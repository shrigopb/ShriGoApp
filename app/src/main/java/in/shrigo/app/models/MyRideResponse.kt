package `in`.shrigo.app.models
import java.io.Serializable
data class MyRideResponse(

    val rideId: Int,
    val rideDate: String?,
    val rideSource: String?,
    val rideDesti: String?,
    val rideVia: String?,
    val rideTime: String?,
    val rideSeats: String?,
    val ridePrice: String?,
    val driverContact: String?,
    val driverUniqueId: String?,
    val driverFirstName: String?

) : Serializable