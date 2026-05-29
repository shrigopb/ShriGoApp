package `in`.shrigo.app.screens.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.utils.SessionManager

@Composable
fun ProfileScreen(

    navController:
    NavController,

    viewModel:
    ProfileViewModel =
        viewModel()

) {

    val context =

        LocalContext
            .current

    val sessionManager =

        SessionManager(
            context
        )

    val userId =

        sessionManager
            .getUserId()

    val role =

        sessionManager
            .getRole()

    val profile by
    viewModel
        .profile
        .collectAsState()

    val isLoading by
    viewModel
        .isLoading
        .collectAsState()

    val error by
    viewModel
        .error
        .collectAsState()

    LaunchedEffect(
        Unit
    ) {

        if (
            userId > 0
        ) {

            viewModel
                .loadProfile(

                    userId,
                    role
                )
        }
    }

    Scaffold { paddingValues ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(
                        paddingValues
                    )
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .padding(
                        horizontal = 20.dp
                    )
                    .padding(
                        bottom = 100.dp
                    ),

            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {

            Text(

                text =
                    "My Profile",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(
                        20.dp
                    )
            )

            when {

                isLoading -> {

                    CircularProgressIndicator()
                }

                error != null -> {

                    Text(
                        text =
                            error ?: ""
                    )
                }

                profile != null -> {

                    Text(
                        text =

                            "${profile!!.firstName} " +
                                    "${profile!!.lastName}",

                        style =
                            MaterialTheme
                                .typography
                                .headlineSmall,

                        fontWeight =
                            FontWeight.Bold
                    )

                    Text(
                        text =
                            profile!!.role
                                ?: ""
                    )

                    Spacer(
                        modifier =
                            Modifier.height(
                                24.dp
                            )
                    )

                    ProfileItem(
                        "Email",
                        profile!!.email
                    )

                    ProfileItem(
                        "Phone",
                        profile!!.phone
                    )

                    ProfileItem(
                        "Age",
                        profile!!.age
                    )

                    // Driver only
                    if (
                        role ==
                        "Driver"
                    ) {

                        Spacer(
                            modifier =
                                Modifier.height(
                                    24.dp
                                )
                        )

                        Text(
                            text =
                                "Vehicle Details",

                            style =
                                MaterialTheme
                                    .typography
                                    .titleMedium,

                            fontWeight =
                                FontWeight.Bold
                        )

                        Spacer(
                            modifier =
                                Modifier.height(
                                    12.dp
                                )
                        )

                        ProfileItem(
                            "Vehicle Model",
                            profile!!
                                .vehicleModel
                        )

                        ProfileItem(
                            "Registration No",
                            profile!!
                                .vehicleRegNo
                        )

                        ProfileItem(
                            "Insurance",
                            profile!!
                                .vehicleInsur
                        )

                        ProfileItem(
                            "Subscription",
                            profile!!
                                .subscription
                        )
                    }

                    Spacer(
                        modifier =
                            Modifier.height(
                                30.dp
                            )
                    )

                    Button(

                        onClick = {

                            sessionManager
                                .logout()

                            navController
                                .navigate(

                                    "${Routes.HOME}/Guest"

                                ) {

                                    popUpTo(0)

                                    launchSingleTop =
                                        true
                                }
                        },

                        modifier =
                            Modifier
                                .fillMaxWidth()
                    ) {

                        Text(
                            "Logout"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(

    title: String,
    value: String?

) {

    Column(

        modifier =
            Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 12.dp
                )
    ) {

        Text(

            text =
                title,

            fontWeight =
                FontWeight.Bold
        )

        Text(
            text =
                value ?: "-"
        )
    }
}