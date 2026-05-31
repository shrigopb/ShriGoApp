package `in`.shrigo.app.screens.rides


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.utils.SessionManager
import androidx.compose.material3.ExperimentalMaterial3Api
import android.app.DatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.Toast

@OptIn(
    ExperimentalMaterial3Api::class
)
@Composable
fun UploadRideScreen(

    navController:
    NavController,

    viewModel:
    UploadRideViewModel =
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

    //----------------------------------
    // Session Values
    //----------------------------------

    val role =
        sessionManager
            .getRole()

    val isAdmin =

        role.equals(
            "Admin",
            true
        )

    //----------------------------------
    // Form States
    //----------------------------------

    var rideSource by remember {
        mutableStateOf("")
    }

    var rideDesti by remember {
        mutableStateOf("")
    }

    var rideVia by remember {
        mutableStateOf("")
    }

    var rideDate by remember {
        mutableStateOf("")
    }

    var rideTime by remember {
        mutableStateOf("")
    }

    var rideSeats by remember {
        mutableStateOf("")
    }

    var ridePrice by remember {
        mutableStateOf("")
    }

    var driverName by remember {

        mutableStateOf(

            if (isAdmin)
                ""

            else
                sessionManager
                    .getFirstName()
        )
    }

    var driverPhone by remember {

        mutableStateOf(

            if (isAdmin)
                ""

            else
                sessionManager
                    .getPhone()
        )
    }

    //----------------------------------
    // ViewModel States
    //----------------------------------

    val isLoading by
    viewModel
        .isLoading
        .collectAsState()

    val uploadSuccess by
    viewModel
        .uploadSuccess
        .collectAsState()

    val error by
    viewModel
        .error
        .collectAsState()

    //----------------------------------
    // Success Navigation
    //----------------------------------

    LaunchedEffect(uploadSuccess) {

        if (uploadSuccess) {

            Toast.makeText(
                context,
                "Ride uploaded successfully",
                Toast.LENGTH_SHORT
            ).show()

            navController.navigate(
                Routes.MY_RIDES
            ) {

                popUpTo(
                    Routes.UPLOAD_RIDE
                ) {
                    inclusive = true
                }
            }
        }
    }

    //----------------------------------
    // UI
    //----------------------------------

     Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(
                bottom = 100.dp
            )
            .verticalScroll(
                rememberScrollState()
            )
    )
    {

        Text(

            text =
                "Upload Ride",

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


        //----------------------------------
        // Ride Source Dropdown
        //----------------------------------
        val rideLocations = listOf(

            "HYD",
            "JBS",
            "Airport",
            "MTPL",
            "KRTL",
            "RYKL",
            "ARMR",
            "NZB",
            "JBS,HYD",
            "JBS,Airport",
            "JBS,HYD,Airport",
            "KRTL,MTPL",
            "RYKL,KRTL,MTPL"
        )

        var expandedSource by remember {
            mutableStateOf(false)
        }
        var expandedVia by remember {
            mutableStateOf(false)
        }
        var expandedTime by remember {
            mutableStateOf(false)
        }

        ExposedDropdownMenuBox(

            expanded =
                expandedSource,

            onExpandedChange = {

                expandedSource =
                    !expandedSource
            }

        ) {

            OutlinedTextField(

                value =
                    rideSource,

                onValueChange = {},

                readOnly =
                    true,

                label = {
                    Text("Ride Source")
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor()
            )

            ExposedDropdownMenu(

                expanded =
                    expandedSource,

                onDismissRequest = {

                    expandedSource =
                        false
                }

            ) {

                rideLocations.forEach {

                    DropdownMenuItem(

                        text = {
                            Text(it)
                        },

                        onClick = {

                            rideSource =
                                it

                            expandedSource =
                                false
                        }
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )


//----------------------------------
// Ride Destination Dropdown
//----------------------------------

        val rideDestilist = listOf(

            "HYD",
            "JBS",
            "Airport",
            "MTPL",
            "KRTL",
            "RYKL",
            "ARMR",
            "NZB",
            "JBS,HYD",
            "JBS,Airport",
            "JBS,HYD,Airport",
            "KRTL,MTPL",
            "RYKL,KRTL,MTPL"
        )

        var expandedDesti by remember {
            mutableStateOf(false)
        }

        ExposedDropdownMenuBox(

            expanded =
                expandedDesti,

            onExpandedChange = {

                expandedDesti =
                    !expandedDesti
            }

        ) {

            OutlinedTextField(

                value =
                    rideDesti,

                onValueChange = {},

                readOnly =
                    true,

                label = {
                    Text("Ride Desti")
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor()
            )

            ExposedDropdownMenu(

                expanded =
                    expandedDesti,

                onDismissRequest = {

                    expandedDesti =
                        false
                }

            ) {

                rideDestilist.forEach {

                    DropdownMenuItem(

                        text = {
                            Text(it)
                        },

                        onClick = {

                            rideDesti =
                                it

                            expandedDesti =
                                false
                        }
                    )
                }
            }
        }
        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //--------------------------------------------------
        //Ride Via
        //------------------------------------------------
        val viaRoutes = listOf(
            "Vemulawada,SDT", "Armr,Kamareddy", "KNR,SDT", "Kamareddy"
        )
        ExposedDropdownMenuBox(

            expanded =
                expandedVia,

            onExpandedChange = {

                expandedVia =
                    !expandedVia
            }

        ) {

            OutlinedTextField(

                value =
                    rideVia,

                onValueChange = {},

                readOnly =
                    true,

                label = {
                    Text("Via")
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor()
            )

            ExposedDropdownMenu(

                expanded =
                    expandedVia,

                onDismissRequest = {

                    expandedVia =
                        false
                }

            ) {

                viaRoutes.forEach {

                    DropdownMenuItem(

                        text = {
                            Text(it)
                        },

                        onClick = {

                            rideVia =
                                it

                            expandedVia =
                                false
                        }
                    )
                }
            }
        }
        Spacer(
            modifier =
                Modifier.height(12.dp)
        )
        //--------------------------------------------------
        //Ride Date
        //------------------------------------------------
         val calendar =

            Calendar.getInstance()

        OutlinedTextField(

            value =
                rideDate,

            onValueChange = {},

            readOnly =
                true,

            label = {
                Text("Ride Date")
            },

            modifier =
                Modifier.fillMaxWidth(),

            trailingIcon = {

                TextButton(

                    onClick = {

                        DatePickerDialog(

                            context,

                            { _, year, month, dayOfMonth ->

                                val selectedCalendar =

                                    Calendar
                                        .getInstance()

                                selectedCalendar.set(

                                    year,
                                    month,
                                    dayOfMonth
                                )

                                rideDate =

                                    SimpleDateFormat(

                                        "yyyy-MM-dd",

                                        Locale
                                            .getDefault()

                                    ).format(

                                        selectedCalendar
                                            .time
                                    )
                            },

                            calendar.get(
                                Calendar.YEAR
                            ),

                            calendar.get(
                                Calendar.MONTH
                            ),

                            calendar.get(
                                Calendar.DAY_OF_MONTH
                            )

                        ).show()
                    }

                ) {

                    Text("📅")
                }
            }
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //-----------------------------------
        //Ride time
        //-----------------------------------
       val rideTimes = listOf(

            "01:00 am",
            "02:00 am",
            "03:00 am",
            "04:00 am",
            "05:00 am",
            "06:00 am",
            "07:00 am",
            "08:00 am",
            "09:00 am",
            "10:00 am",
            "11:00 am",
            "12:00 am",

            "01:00 pm",
            "02:00 pm",
            "03:00 pm",
            "04:00 pm",
            "05:00 pm",
            "06:00 pm",
            "07:00 pm",
            "08:00 pm",
            "09:00 pm",
            "10:00 pm",
            "11:00 pm",
            "12:00 pm"
        )

        ExposedDropdownMenuBox(

            expanded =
                expandedTime,

            onExpandedChange = {

                expandedTime =
                    !expandedTime
            }

        ) {

            OutlinedTextField(

                value =
                    rideTime,

                onValueChange = {},

                readOnly =
                    true,

                label = {
                    Text("Time")
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor()
            )

            ExposedDropdownMenu(

                expanded =
                    expandedTime,

                onDismissRequest = {

                    expandedTime =
                        false
                }

            ) {

                rideTimes.forEach {

                    DropdownMenuItem(

                        text = {
                            Text(it)
                        },

                        onClick = {

                            rideTime =
                                it

                            expandedTime =
                                false
                        }
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //--------------------------------
        //Rime Seats
        //----------------------------
        var expandedSeats by remember {
            mutableStateOf(false)
        }

        val seatOptions = listOf( "1", "2", "3", "4", "5", "6" )
         ExposedDropdownMenuBox(

            expanded =
                expandedSeats,

            onExpandedChange = {

                expandedSeats =
                    !expandedSeats
            }

        ) {

            OutlinedTextField(

                value =
                    rideSeats,

                onValueChange = {},

                readOnly =
                    true,

                label = {
                    Text("Seats")
                },

                modifier =
                    Modifier
                        .fillMaxWidth()
                        .menuAnchor()
            )

            ExposedDropdownMenu(

                expanded =
                    expandedSeats,

                onDismissRequest = {

                    expandedSeats =
                        false
                }

            ) {

                seatOptions.forEach {

                    DropdownMenuItem(

                        text = {
                            Text(it)
                        },

                        onClick = {

                            rideSeats =
                                it

                            expandedSeats =
                                false
                        }
                    )
                }
            }
        }


        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = ridePrice,
            onValueChange = {
                ridePrice = it
            },
            label = {
                Text("Price")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = driverName,
            onValueChange = {
                driverName = it
            },
            label = {
                Text("Driver Name")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = driverPhone,
            onValueChange = {
                driverPhone = it
            },
            label = {
                Text("Driver Phone")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        Button(

            onClick = {

                val request =

                    UploadRideRequest(

                        rideDate =
                            rideDate,

                        rideSource =
                            rideSource,

                        rideDesti =
                            rideDesti,

                        rideVia =
                            rideVia,

                        rideTime = convertTo24Hour(rideTime),

                        rideSeats =
                            rideSeats,

                        ridePrice =
                            ridePrice,

                        driverContact =
                            driverPhone,

                        driverUniqueId =

                            if (isAdmin)

                                "64782"

                            else

                                sessionManager
                                    .getUserId()
                                    .toString(),

                        driverFirstName =
                            driverName
                    )

                viewModel
                    .uploadRide(
                        request
                    )
            },

            modifier =
                Modifier.fillMaxWidth()

        ) {

            Text(

                if (
                    isLoading
                )

                    "Uploading..."

                else

                    "Upload Ride"
            )
        }

        error?.let {

            Spacer(
                modifier =
                    Modifier.height(
                        12.dp
                    )
            )

            Text(
                text = it
            )
        }
    }
}
//Convertor ampmtime
private fun convertTo24Hour(ampmTime: String): String {

    return try {

        val inputFormat =

            java.text.SimpleDateFormat(
                "hh:mm a",
                java.util.Locale.ENGLISH
            )

        val outputFormat =

            java.text.SimpleDateFormat(
                "HH:mm:ss",
                java.util.Locale.ENGLISH
            )

        val date =
            inputFormat.parse(
                ampmTime
                    .uppercase()
            )

        outputFormat.format(date!!)

    } catch (e: Exception) {

        "00:00:00"
    }
}

