package `in`.shrigo.app.screens.bookings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.shrigo.app.screens.bookings.BookingsViewModel
import `in`.shrigo.app.utils.SessionManager
import android.content.Context
import android.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun BookingScreen() {

    val context =

        LocalContext.current

    val sessionManager =

        remember {

            SessionManager(
                context
            )
        }

    val viewModel:
            BookingsViewModel =

        viewModel()
    val role =
        sessionManager
            .getRole()

    val bookings by

    viewModel
        .bookings
        .collectAsState()

    val isLoading by

    viewModel
        .isLoading
        .collectAsState()

    val error by

    viewModel
        .error
        .collectAsState()

    //--------------------------------
    // Load bookings
    //--------------------------------
    LaunchedEffect(
        Unit
    ) {

        if (

            role.equals(

                "Driver",

                ignoreCase =
                    true
            )
        ) {

            viewModel
                .loadDriverBookings(

                    sessionManager
                        .getUserUniqueId()
                )

        } else {

            viewModel
                .loadMyBookings(

                    sessionManager
                        .getUserUniqueId()
                )
        }
    }
    LaunchedEffect(
        Unit
    ) {

        viewModel
            .loadMyBookings(

                sessionManager
                    .getUserUniqueId()
            )
    }

    //--------------------------------
    // UI
    //--------------------------------

    Box(

        modifier =

            Modifier
                .fillMaxSize()
    ) {

        when {

            isLoading -> {

                CircularProgressIndicator(

                    modifier =

                        Modifier
                            .align(
                                Alignment.Center
                            )
                )
            }

            error != null -> {

                Text(

                    text =

                        error
                            ?: "Error",

                    modifier =

                        Modifier
                            .align(
                                Alignment.Center
                            )
                )
            }

            bookings.isEmpty() -> {

                Text(

                    text =

                        "No bookings found",

                    style =

                        MaterialTheme
                            .typography
                            .headlineSmall,

                    modifier =

                        Modifier
                            .align(
                                Alignment.Center
                            )
                )
            }

            else -> {

                LazyColumn(

                    modifier =

                        Modifier
                            .fillMaxSize(),

                    contentPadding =

                        PaddingValues(
                            12.dp
                        ),

                    verticalArrangement =

                        Arrangement
                            .spacedBy(
                                10.dp
                            )
                ) {

                    items(
                        bookings
                    ) {

                            booking ->

                        Card(

                            modifier =

                                Modifier
                                    .fillMaxWidth()
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

                                        "${booking.rideSource} → ${booking.rideDesti}",

                                    style =

                                        MaterialTheme
                                            .typography
                                            .titleMedium
                                )

                                Spacer(

                                    modifier =

                                        Modifier
                                            .height(
                                                6.dp
                                            )
                                )

                                Text(

                                    text =

                                        "Date: ${booking.rideDate}"
                                )

                                Text(

                                    text =

                                        "Time: ${booking.rideTime}"
                                )

                                Text(

                                    text =

                                        "Seats: ${booking.bookedSeats}"
                                )

                                Text(

                                    text =

                                        "Price: ₹${booking.ridePrice}"
                                )

                                if (

                                    role.equals(

                                        "Driver",

                                        ignoreCase =
                                            true
                                    )
                                ) {

                                    Text(

                                        text =

                                            "Passenger: ${booking.passengerFirstName}"
                                    )

                                    Text(

                                        text =

                                            "Contact: ${booking.passengerContact}"
                                    )

                                } else {

                                    Text(

                                        text =

                                            "Driver: ${booking.driverFirstName}"
                                    )

                                    Text(

                                        text =

                                            "Contact: ${booking.driverContact}"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}