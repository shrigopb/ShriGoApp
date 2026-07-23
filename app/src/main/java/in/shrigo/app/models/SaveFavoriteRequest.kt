package `in`.shrigo.app.models

data class SaveFavoriteRequest(

    val driverUniqueId: String,

    val favoriteName: String,

    val routeName: String,

    val rideFrom: String,

    val rideVia: String,

    val rideTo: String,

    val rideTime: String,

    val ridePrice: Double,

    val rideSeats: Int
)