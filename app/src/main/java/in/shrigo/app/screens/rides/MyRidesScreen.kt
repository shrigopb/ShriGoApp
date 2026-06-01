package `in`.shrigo.app.screens.rides

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.models.MyRideResponse
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.utils.SessionManager

@Composable
fun MyRidesScreen(

    navController:
    NavController,

    viewModel:
    MyRidesViewModel =
        viewModel()

) {

    val context =
        LocalContext.current

    val sessionManager =
        remember {

            SessionManager(
                context
            )
        }

    val role =

        sessionManager
            .getRole()

    val uniqueId =

        sessionManager
            .getUserUniqueId()
            .toString()

    val rides by
    viewModel
        .rides
        .collectAsState()

    val isLoading by
    viewModel
        .isLoading
        .collectAsState()

    val canUpload =

        role.equals(
            "Driver",
            true
        )

                ||

                role.equals(
                    "Admin",
                    true
                )

    //-----------------------------------
    // Load rides
    //-----------------------------------

    LaunchedEffect(
        Unit
    ) {

        Log.d(
            "MY_RIDES",
            "UniqueId = $uniqueId"
        )
        viewModel
            .loadMyRides(
                uniqueId
            )
    }

    Scaffold(

        floatingActionButton = {

            if (
                canUpload
            ) {

                FloatingActionButton(

                    onClick = {

                        navController
                            .navigate(
                                Routes
                                    .UPLOAD_RIDE
                            )
                    }

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            "Upload Ride"
                    )
                }
            }
        }

    ) { paddingValues ->

        Column(

            modifier =

                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .padding(16.dp)
        ) {

            Text(

                text =
                    "My Rides",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium
            )

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )

            when {

                isLoading -> {

                    CircularProgressIndicator()
                }

                rides.isEmpty() -> {

                    Text(
                        text =
                            "No rides uploaded yet"
                    )
                }

                else -> {

                    LazyColumn(

                        verticalArrangement =

                            Arrangement
                                .spacedBy(
                                    12.dp
                                )
                    ) {

                        items(
                            rides
                        ) { ride ->

                            RideCard(
                                ride
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RideCard(

    ride:
    MyRideResponse

) {

    Card(

        modifier =
            Modifier
                .fillMaxWidth(),

        elevation =
            CardDefaults
                .cardElevation(
                    defaultElevation =
                        4.dp
                )
    ) {

        Column(

            modifier =
                Modifier
                    .padding(
                        16.dp
                    )
        ) {

            Text(

                text =

                    "${ride.rideSource}" +
                            " → " +
                            "${ride.rideDesti}",

                style =
                    MaterialTheme
                        .typography
                        .titleMedium
            )

            Spacer(
                modifier =
                    Modifier.height(
                        6.dp
                    )
            )

            Text(
                text =
                    "Date: ${ride.rideDate}"
            )

            val formattedTime = try {

                val inputFormat =

                    java.text.SimpleDateFormat(
                        "HH:mm:ss",
                        java.util.Locale.getDefault()
                    )

                val outputFormat =

                    java.text.SimpleDateFormat(
                        "hh:mm a",
                        java.util.Locale.getDefault()
                    )

                val date =

                    inputFormat.parse(
                        ride.rideTime
                    )

                outputFormat.format(
                    date!!
                )

            } catch (
                e: Exception
            ) {

                ride.rideTime
            }

            Text(
                text =
                    "Time: $formattedTime"
            )

            Text(
                text =
                    "Seats: ${ride.rideSeats}"
            )

            Text(
                text =
                    "Price: ₹${ride.ridePrice}"
            )

            Text(
                text =
                    "Via: ${ride.rideVia}"
            )
        }
    }
}