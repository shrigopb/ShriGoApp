package `in`.shrigo.app.screens.rides

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.utils.SessionManager

@Composable
fun MyRidesScreen(

    navController:
    NavController

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

    Scaffold(

        floatingActionButton = {

            if (
                canUpload
            ) {

                FloatingActionButton( modifier = Modifier.padding( bottom = 72.dp ),

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

        Box(

            modifier =

                Modifier
                    .fillMaxSize()
                   .padding(
                    paddingValues
                         )
                    .padding(
                        bottom = 90.dp
                    ),

            contentAlignment =
                Alignment.Center
        ) {


            Column(

                horizontalAlignment =
                    Alignment.CenterHorizontally
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
                        Modifier.height(12.dp)
                )

                Text(
                    text =
                        "Role: $role"
                )

                Text(
                    text =
                        "Can Upload: $canUpload"
                )
            }

        }
    }
}
