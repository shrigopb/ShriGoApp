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

    LaunchedEffect(
        uploadSuccess
    ) {

        if (
            uploadSuccess
        ) {

            navController
                .navigate(
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

        OutlinedTextField(
            value = rideSource,
            onValueChange = {
                rideSource = it
            },
            label = {
                Text("Ride Source")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = rideDesti,
            onValueChange = {
                rideDesti = it
            },
            label = {
                Text("Ride Destination")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = rideVia,
            onValueChange = {
                rideVia = it
            },
            label = {
                Text("Via")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = rideDate,
            onValueChange = {
                rideDate = it
            },
            label = {
                Text("Date (yyyy-MM-dd)")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = rideTime,
            onValueChange = {
                rideTime = it
            },
            label = {
                Text("Time (HH:mm)")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = rideSeats,
            onValueChange = {
                rideSeats = it
            },
            label = {
                Text("Seats")
            },
            modifier =
                Modifier.fillMaxWidth()
        )

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

                        rideTime =
                            rideTime,

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

