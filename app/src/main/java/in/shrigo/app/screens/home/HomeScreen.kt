package `in`.shrigo.app.screens.home

import android.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.People
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.material.icons.filled.Call
import androidx.compose.ui.platform.LocalContext
import java.time.format.DateTimeFormatter
import java.time.LocalTime
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import `in`.shrigo.app.models.BookRideRequest
import `in`.shrigo.app.repository.BookingRepository
import `in`.shrigo.app.utils.SessionManager
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    firstName: String,
    navController: NavHostController,
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

                        Spacer(
                            modifier =
                                Modifier.height(4.dp)
                        )

                        Text(
                            text =
                                "Welcome : $firstName",

                            style =
                                MaterialTheme
                                    .typography
                                    .bodyMedium,

                            fontWeight =
                                FontWeight.Medium,

                            color =
                                Color.DarkGray
                        )
                    }
                },

                actions = {

                    BadgedBox(

                        badge = {

                            Badge {

                                Text("1")
                            }
                        }

                    ) {

                        IconButton(

                            onClick = {

                                Log.d(
                                    "NOTIFICATION",
                                    "Bell clicked"
                                )

                                navController.navigate(
                                    "notifications"
                                )
                            }
                        ) {

                            Icon(

                                imageVector =
                                    Icons.Default.Notifications,

                                contentDescription =
                                    "Notifications"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->

            when {

                isLoading -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        CircularProgressIndicator()
                    }
                }

                errorMessage != null -> {

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),

                        contentAlignment =
                            Alignment.Center
                    ) {

                        Text(
                            text =
                                errorMessage
                                    ?: "Unknown Error",

                            color =
                                Color.Red
                        )
                    }
                }

                else -> {

                    LazyColumn(

                        modifier =
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    paddingValues
                                )
                                .background(
                                    Color(
                                        0xFFF5F5F5
                                    )
                                ),

                        contentPadding =
                            PaddingValues(
                                bottom = 24.dp
                            )
                    ) {

                        item {

                            SearchRideCard()

                            Text(

                                text =
                                    "Available Rides",

                                style =
                                    MaterialTheme
                                        .typography
                                        .titleLarge,

                                fontWeight =
                                    FontWeight.Bold,

                                modifier =
                                    Modifier.padding(

                                        horizontal =
                                            16.dp,

                                        vertical =
                                            8.dp
                                    )
                            )
                        }

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

    val context =
        LocalContext.current

    val sessionManager =
        SessionManager(
            context
        )

    val role =
        sessionManager
            .getRole()
    val coroutineScope =
        rememberCoroutineScope()

    val bookingRepository =
        remember {
            BookingRepository()
        }

    Card(

        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),

        shape =
            RoundedCornerShape(
                20.dp
            ),

        elevation =
            CardDefaults.cardElevation(
                defaultElevation = 6.dp
            )

    ) {

        Column(

            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(18.dp)

        ) {
            val formattedTime = try {

                if (ride.rideTime == null) {
                    "-"
                } else {

                    val inputFormat =
                        SimpleDateFormat(
                            "HH:mm:ss",
                            Locale.getDefault()
                        )

                    val outputFormat =
                        SimpleDateFormat(
                            "hh:mm a",
                            Locale.getDefault()
                        )

                    val startDate =
                        inputFormat.parse(
                            ride.rideTime.toString()
                        )

                    if (startDate == null) {
                        "-"
                    } else {

                        val calendar =
                            Calendar.getInstance()

                        calendar.time = startDate

                        val startTime =
                            outputFormat.format(
                                calendar.time
                            )

                        calendar.add(
                            Calendar.HOUR,
                            2
                        )

                        val endTime =
                            outputFormat.format(
                                calendar.time
                            )

                        "$startTime - $endTime"
                    }
                }

            } catch (e: Exception) {
                "-"
            }

            // DATE + TIME
            val displayDate = try {

                val inputFormat =

                    SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.getDefault()
                    )

                val rideDateObj =

                    inputFormat.parse(
                        ride.rideDate
                    )

                val todayCalendar =

                    Calendar.getInstance()

                val today =

                    inputFormat.format(
                        todayCalendar.time
                    )

                val tomorrowCalendar =

                    Calendar.getInstance()

                tomorrowCalendar.add(
                    Calendar.DAY_OF_YEAR,
                    1
                )

                val tomorrow =

                    inputFormat.format(
                        tomorrowCalendar.time
                    )

                val currentRideDate =

                    inputFormat.format(
                        rideDateObj!!
                    )

                when (
                    currentRideDate
                ) {

                    today ->
                        "Today"

                    tomorrow ->
                        "Tomorrow"

                    else ->
                        ride.rideDate
                            ?: "-"

                }

            } catch (
                e: Exception
            ) {

                ride.rideDate
                    ?: "-"
            }
//----------------------------------
// SOURCE + SHARE
//----------------------------------

            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Row(

                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    if (
                        ride.rideSource
                            ?.contains(
                                "Airport",
                                ignoreCase = true
                            ) == true
                    ) {

                        Icon(
                            imageVector =
                                if (
                                    ride.rideSource
                                        ?.contains(
                                            "Airport",
                                            ignoreCase = true
                                        ) == true
                                ) {
                                    Icons.Default.AirplanemodeActive
                                } else {
                                    Icons.Default.LocationOn
                                },

                            contentDescription = null,

                            tint =
                                if (
                                    ride.rideSource
                                        ?.contains(
                                            "Airport",
                                            ignoreCase = true
                                        ) == true
                                ) {
                                    Color(0xFF0288D1)
                                } else {
                                    Color.Gray
                                }
                        )

                    } else {

                        Icon(

                            imageVector =
                                Icons.Default
                                    .LocationOn,

                            contentDescription =
                                null,

                            tint =
                                Color.Gray
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.width(
                                6.dp
                            )
                    )

                    Text(

                        text =
                            ride.rideSource
                                ?: "Unknown",

                        style =
                            MaterialTheme
                                .typography
                                .titleLarge,

                        fontWeight =
                            FontWeight.Bold
                    )
                }

                IconButton(

                    onClick = {

                        val shareMessage = """

🚗 Ride Available on ShriGo

📍 From:
${ride.rideSource ?: "-"}

📍 To:
${ride.rideDesti ?: "-"}

🛣 Via:
${ride.rideVia ?: "-"}

📅 Date:
$displayDate

⏰ Time:
$formattedTime

💺 Seats:
${ride.rideSeats ?: 0}

💰 Price:
₹${ride.ridePrice ?: 0} / seat

Book your ride on ShriGo:
https://shrigo-cmc6eaccfzfzfxdf.canadacentral-01.azurewebsites.net

            """.trimIndent()

                        val shareIntent =

                            Intent(
                                Intent.ACTION_SEND
                            ).apply {

                                type =
                                    "text/plain"

                                putExtra(

                                    Intent.EXTRA_TEXT,

                                    shareMessage
                                )
                            }

                        context.startActivity(

                            Intent.createChooser(

                                shareIntent,

                                "Share Ride"
                            )
                        )
                    },

                    modifier =
                        Modifier
                            .size(42.dp)
                            .background(

                                Color(
                                    0xFFFFC107
                                ),

                                RoundedCornerShape(
                                    10.dp
                                )
                            )
                ) {
                    //Share Icon
                    Icon(

                        imageVector =
                            Icons.Default.Share,

                        contentDescription =
                            "Share Ride",

                        tint =
                            Color.Black
                    )
                }
            }
            //-------------------

            Spacer(
                modifier =
                    Modifier.height(
                        4.dp
                    )
            )



            Text(
                text =
                    "$displayDate • $formattedTime",

                style =
                    MaterialTheme
                        .typography
                        .bodySmall,

                color = when (
                    displayDate
                ) {

                    "Today" ->
                        Color(
                            0xFF2E7D32
                        )

                    "Tomorrow" ->
                        Color.Blue

                    else ->
                        Color.Gray
                },

                fontWeight =
                    FontWeight.SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(
                        16.dp
                    )
            )

            HorizontalDivider()

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )
            //-----------------------------
            // VIA + PRICE
            //-----------------------------
            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Column {

                    Text(
                        text = "Via",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            Color.Gray
                    )

                    Text(
                        text = ride.rideVia ?: "-",
                        fontWeight = FontWeight.Medium
                    )
                }

                Column(
                    horizontalAlignment =
                        Alignment.End
                ) {

                    Text(
                        text = "Price",

                        style =
                            MaterialTheme
                                .typography
                                .bodySmall,

                        color =
                            Color.Gray
                    )

                    Text(
                        text = "₹${ride.ridePrice ?: 0} /seat",
                        color = Color(0xFF2E7D32),
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )

            HorizontalDivider()

            Spacer(
                modifier =
                    Modifier.height(
                        14.dp
                    )
            )
            //-------------------------------
            // DESTINATION + SEATS
            //-------------------------------
            Row(

                modifier =
                    Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    val isAirport =

                        (ride.rideSource
                            ?.contains(
                                "Airport",
                                ignoreCase = true
                            ) == true)

                                ||

                                (ride.rideDesti
                                    ?.contains(
                                        "Airport",
                                        ignoreCase = true
                                    ) == true)

                    Icon(
                        imageVector =
                            if (
                                ride.rideDesti
                                    ?.contains(
                                        "Airport",
                                        ignoreCase = true
                                    ) == true
                            ) {
                                Icons.Default.AirplanemodeActive
                            } else {
                                Icons.Default.LocationOn
                            },

                        contentDescription = null,

                        tint =
                            if (
                                ride.rideDesti
                                    ?.contains(
                                        "Airport",
                                        ignoreCase = true
                                    ) == true
                            ) {
                                Color(0xFF0288D1)
                            } else {
                                Color.Gray
                            }
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                8.dp
                            )
                    )

                    Text(
                        text =
                            ride.rideDesti
                                ?: "Unknown"
                    )
                }

                Row(
                    verticalAlignment =
                        Alignment.CenterVertically
                ) {

                    Icon(
                        imageVector =
                            Icons.Default.People,

                        contentDescription =
                            null,

                        tint =
                            Color.Gray
                    )

                    Spacer(
                        modifier =
                            Modifier.width(
                                4.dp
                            )
                    )

                    Text(
                        text =
                            "${ride.rideSeats ?: 0} Seats"
                    )
                }
            }
            //----------------------------------------------

            Spacer(
                modifier =
                    Modifier.height(
                        18.dp
                    )
            )
            //-----------------------------------
            // BOOK BUTTON
            //----------------------------------
            if (

                role.equals(

                    "Passenger",

                    ignoreCase =
                        true
                )
            ) {

                Button(

                    onClick = {

                        coroutineScope.launch {

                            val request =

                                BookRideRequest(

                                    rideId =
                                        ride.rideId,

                                    bookedSeats =
                                        1,

                                    passengerFirstName =

                                        sessionManager
                                            .getFirstName(),

                                    passengerUniqueId =

                                        sessionManager
                                            .getUserUniqueId(),

                                    passengerContact =

                                        sessionManager
                                            .getPhone(),

                                    passengerEmail =

                                        sessionManager
                                            .getEmail()
                                )

                            val success =

                                bookingRepository
                                    .bookRide(
                                        request
                                    )

                            Toast.makeText(

                                context,

                                if (success)
                                    "Ride booked successfully"
                                else
                                    "Booking failed",

                                Toast.LENGTH_SHORT

                            ).show()
                        }
                    },

                    modifier =
                        Modifier
                            .fillMaxWidth(),

                    colors =
                        ButtonDefaults
                            .buttonColors(

                                containerColor =
                                    Color(
                                        0xFF2196F3
                                    )
                            ),

                    shape =
                        RoundedCornerShape(
                            12.dp
                        )
                ) {

                    Text(
                        "Book Ride"
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            // CALL DRIVER BUTTON
            OutlinedButton(

                onClick = {

                    val intent = Intent(

                        Intent.ACTION_DIAL,

                        Uri.parse(
                            "tel:${
                                ride.driverContact
                                    ?: ""
                            }"
                        )
                    )

                    context.startActivity(
                        intent
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),
                colors =
                    ButtonDefaults
                        .buttonColors(

                            containerColor =
                                Color(
                                    0xFF008000
                                )
                        ),
                shape =
                    RoundedCornerShape(
                        14.dp
                    )
            ) {

                Icon(

                    imageVector =
                        Icons.Default.Call,

                    contentDescription =
                        "Call Driver"
                )

                Spacer(
                    modifier =
                        Modifier.width(
                            8.dp
                        )
                )

                Text(
                    text =
                        "Call Driver"
                )
            }
        }
    }
}