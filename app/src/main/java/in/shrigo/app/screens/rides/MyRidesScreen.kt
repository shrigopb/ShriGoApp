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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

@Composable
fun MyRidesScreen(

    navController:
    NavController,

    viewModel: MyRidesViewModel = viewModel()

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
    val deleteSuccess by viewModel.deleteSuccess.collectAsState()

    val favoriteSaved by viewModel.favoriteSaved.collectAsState()
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

    LaunchedEffect(favoriteSaved) {

        if (favoriteSaved) {

            Toast.makeText(
                context,
                "Route added to Favorites",
                Toast.LENGTH_SHORT
            ).show()

            viewModel.clearFavoriteSaved()
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

                                onEdit = {

                                    navController
                                        .currentBackStackEntry
                                        ?.savedStateHandle
                                        ?.set(
                                            "ride",
                                            ride
                                        )

                                    navController.navigate(
                                        Routes.EDIT_RIDE
                                    )
                                },

                                onDelete = {

                                    rideToDelete =
                                        ride
                                },
                                onFavorite = {

                                    viewModel.saveFavorite(it)
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
    onEdit: () -> Unit,
    onDelete:
        () -> Unit,
    onFavorite: (MyRideResponse) -> Unit

) {
    var isFavorite by remember {
        mutableStateOf(false)
    }
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

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically

            ) {

                Text(

                    text =
                        "${ride.rideSource} → ${ride.rideDesti}",
                    modifier = Modifier.weight(1f),

                    maxLines = 2,

                    overflow = TextOverflow.Ellipsis,
                    style =
                        MaterialTheme
                            .typography
                            .titleMedium
                )

                IconButton(

                    enabled = !isFavorite,

                    onClick = {

                        onFavorite(ride)

                        isFavorite = true
                    }

                ) {

                    Icon(

                        imageVector =
                            if (isFavorite)
                                Icons.Default.Star
                            else
                                Icons.Default.StarBorder,

                        tint =
                            if (isFavorite)
                                Color(0xFFFFC107)
                            else
                                MaterialTheme.colorScheme.onSurface,

                        contentDescription =
                            if (isFavorite)
                                "Favorite Saved"
                            else
                                "Save Favorite"
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(12.dp)
            )
            val formattedTime = try {

                val inputFormat = java.text.SimpleDateFormat(
                    "HH:mm:ss",
                    java.util.Locale.getDefault()
                )

                val outputFormat = java.text.SimpleDateFormat(
                    "hh:mm a",
                    java.util.Locale.getDefault()
                )

                val date = inputFormat.parse(ride.rideTime)

                outputFormat.format(date!!)

            } catch (e: Exception) {

                ride.rideTime
            }
            Text(
                buildAnnotatedString {

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Date : ")
                    }
                    append("${ride.rideDate}\n\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Time : ")
                    }
                    append("$formattedTime\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Seats : ")
                    }
                    append("${ride.rideSeats}\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Price : ")
                    }
                    append("₹${ride.ridePrice}\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Via : ")
                    }
                    append(ride.rideVia)
                },

                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Button(

                onClick = {
                    onEdit()
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("Edit Ride")
            }

        }
    }
}



