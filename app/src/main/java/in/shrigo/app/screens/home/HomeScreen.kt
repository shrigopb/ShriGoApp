package `in`.shrigo.app.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.shrigo.app.models.Ride
import `in`.shrigo.app.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = viewModel()
) {

    val rides by viewModel.rides.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "ShriGo",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )

                        Text(
                            text = "Premium Car Service",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF5F5F5))
        ) {

            SearchRideCard()

            Text(
                text = "Available Rides",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                )
            )

            when {

                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Unknown Error",
                            color = Color.Red
                        )
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            bottom = 90.dp
                        )
                    ) {

                        items(rides) { ride ->
                            RideCard(ride)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchRideCard() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),

        shape = RoundedCornerShape(16.dp),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 4.dp
            )
    ) {

        Column(
            modifier = Modifier
                .padding(14.dp)
        ) {

            Text(
                text = "Find Your Ride",
                style =
                    MaterialTheme
                        .typography
                        .titleMedium,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Row(
                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.spacedBy(
                        8.dp
                    )
            ) {

                OutlinedTextField(
                    value = "",
                    onValueChange = {},

                    label = {
                        Text("From")
                    },

                    leadingIcon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null
                        )
                    },

                    modifier =
                        Modifier.weight(1f),

                    singleLine = true
                )

                OutlinedTextField(
                    value = "",
                    onValueChange = {},

                    label = {
                        Text("To")
                    },

                    leadingIcon = {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null
                        )
                    },

                    modifier =
                        Modifier.weight(1f),

                    singleLine = true
                )
            }

            Spacer(
                modifier =
                    Modifier.height(12.dp)
            )

            Button(
                onClick = {},

                modifier =
                    Modifier.fillMaxWidth(),

                colors =
                    ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Color(0xFF2E7D32)
                        ),

                shape =
                    RoundedCornerShape(
                        12.dp
                    )
            ) {

                Icon(
                    Icons.Default.Search,
                    contentDescription =
                        null
                )

                Spacer(
                    modifier =
                        Modifier.width(6.dp)
                )

                Text("Search Ride")
            }
        }
    }
}

@Composable
fun RideCard(
    ride: Ride
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text =
                    "${ride.rideSource} → ${ride.rideDesti}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Via: ${ride.rideVia}"
            )

            Text(
                text = "Time: ${ride.rideTime}"
            )

            Text(
                text = "Date: ${ride.rideDate}"
            )

            Text(
                text = "Seats Available: ${ride.rideSeats}"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint = Color.Gray
                )

                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = ride.driverFirstName
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.SpaceBetween,
                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = "₹${ride.ridePrice}",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF6F00)
                )

                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults
                        .buttonColors(
                            containerColor =
                                Color(0xFF2E7D32)
                        )
                ) {
                    Text("Book Ride")
                }
            }
        }
    }
}