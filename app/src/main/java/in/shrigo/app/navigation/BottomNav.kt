package `in`.shrigo.app.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNav(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    object Home : BottomNav(
        route = "home",
        title = "Home",
        icon = Icons.Filled.Home
    )

    object MyRides : BottomNav(
        route = "my_rides",
        title = "My Rides",
        icon = Icons.Filled.DirectionsCar
    )

    object Bookings : BottomNav(
        route = "bookings",
        title = "Bookings",
        icon = Icons.Filled.Book
    )

    object Profile : BottomNav(
        route = "profile",
        title = "Profile",
        icon = Icons.Filled.Person
    )
}