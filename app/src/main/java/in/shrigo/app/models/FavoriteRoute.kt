package `in`.shrigo.app.models

data class FavoriteRoute(

    val id: Int,

    val favoriteName: String?,

    val driverUniqueId: String,

    val routeName: String,

    val rideFrom: String,

    val rideVia: String,

    val rideTo: String,

    val rideTime: String,

    val ridePrice: Double,

    val rideSeats: Int,

    val isActive: Boolean,

    val createdDate: String,

    val modifiedDate: String?,

    val usageCount: Int
)