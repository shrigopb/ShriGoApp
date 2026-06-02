package `in`.shrigo.app.screens.rides

import android.util.Log
import android.widget.Toast
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

    //-----------------------------------
    // Session values
    //-----------------------------------

    val role =

        sessionManager
            .getRole()

    val uniqueId =

        sessionManager
            .getUserUniqueId()

    //-----------------------------------
    // Logs
    //-----------------------------------
    Log.d(
        "MY_RIDES_ROLE",
        "Role = '$role'"
    )
    Log.d(
        "MY_RIDES",
        "Role = $role"
    )
    Log.d(
        "MY_RIDES",
        "UniqueId = $uniqueId"
    )

    //-----------------------------------
    // ViewModel state
    //-----------------------------------

    val rides by
    viewModel
        .rides
        .collectAsState()

    val isLoading by
    viewModel
        .isLoading
        .collectAsState()

    var rideToDelete by remember {

        mutableStateOf<
                MyRideResponse?
                >(null)
    }
    val deleteSuccess by

    viewModel
        .deleteSuccess
        .collectAsState()
    //-----------------------------------
    // Upload permission
    //-----------------------------------
    val canUpload =

        role
            .trim()
            .equals(
                "Driver",
                ignoreCase = true
            )

                ||

                role
                    .trim()
                    .equals(
                        "Admin",
                        ignoreCase = true
                    )

    //-----------------------------------
    // Load rides
    //-----------------------------------

    LaunchedEffect(
        Unit
    ) {

        viewModel
            .loadMyRides(
                uniqueId
            )
    }
    LaunchedEffect(
        deleteSuccess
    ) {

        if (
            deleteSuccess
        ) {

            Toast.makeText(

                context,

                "Ride Deleted Successfully",

                Toast.LENGTH_SHORT

            ).show()

            viewModel
                .clearDeleteSuccess()
        }
    }
    //-----------------------------------
    // UI
    //-----------------------------------

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
                    },

                    modifier =
                        Modifier
                            .padding(
                                bottom = 70.dp
                            )

                ) {

                    Icon(

                        imageVector =
                            Icons.Default.Add,

                        contentDescription =
                            "Upload Ride"
                    )
                }
            }
        },

        floatingActionButtonPosition =
            FabPosition.End

    ) { paddingValues ->

        Column(

            modifier =

                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .padding(16.dp)
                    .padding(
                        bottom = 80.dp
                    )
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
                                ),

                        contentPadding =
                            PaddingValues(
                                bottom = 80.dp
                            )
                    ) {

                        items(
                            rides
                        ) { ride ->

                            RideCard(

                                ride = ride,

                                onDelete = {

                                    rideToDelete =
                                        ride
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    //-----------------------------------
    // Delete Confirmation Dialog
    //-----------------------------------

    if (
        rideToDelete != null
    ) {

        AlertDialog(

            onDismissRequest = {

                rideToDelete =
                    null
            },

            title = {

                Text(
                    "Delete Ride"
                )
            },

            text = {

                Text(
                    "Are you sure you want to delete this ride?"
                )
            },

            confirmButton = {

                TextButton(

                    onClick = {

                        viewModel
                            .deleteRide(

                                rideToDelete!!
                                    .rideId,

                                uniqueId
                            )

                        rideToDelete =
                            null
                    }
                ) {

                    Text(
                        "Delete"
                    )
                }
            },

            dismissButton = {

                TextButton(

                    onClick = {

                        rideToDelete =
                            null
                    }
                ) {

                    Text(
                        "Cancel"
                    )
                }
            }
        )
    }

}

@Composable
fun RideCard(

    ride:
    MyRideResponse,

    onDelete:
        () -> Unit

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
            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Button(

                onClick = {

                    onDelete()
                },

                colors =

                    ButtonDefaults
                        .buttonColors(

                            containerColor =
                                MaterialTheme
                                    .colorScheme
                                    .error
                        )
            ) {

                Text(
                    "Delete Ride"
                )
            }
            //-----------------------------------
            // Time formatting
            //-----------------------------------

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