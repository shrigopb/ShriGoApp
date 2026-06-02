package `in`.shrigo.app.screens.rides

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.models.MyRideResponse

@Composable
fun EditRideScreen(

    navController:
    NavController

) {

    val existingRide =

        navController
            .previousBackStackEntry
            ?.savedStateHandle
            ?.get<MyRideResponse>(
                "ride"
            )

    Column(

        modifier =
            Modifier
                .fillMaxSize()
                .padding(16.dp)

    ) {

        Text(

            text =
                "Edit Ride",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(
                    20.dp
                )
        )

        Text(
            text =
                "Ride Id = ${existingRide?.rideId}"
        )

        Text(
            text =
                "Source = ${existingRide?.rideSource}"
        )

        Text(
            text =
                "Destination = ${existingRide?.rideDesti}"
        )

        Text(
            text =
                "Date = ${existingRide?.rideDate}"
        )
    }
}