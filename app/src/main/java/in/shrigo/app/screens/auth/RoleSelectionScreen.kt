package `in`.shrigo.app.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun RoleSelectionScreen(
    navController: NavController
) {

    Column(

        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement =
            Arrangement.Center,

        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        //----------------------------------
        // Title
        //----------------------------------

        Text(

            text =
                "Choose Account Type",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium,

            fontWeight =
                FontWeight.Bold
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Text(

            text =
                "Select how you want to use ShriGo",

            style =
                MaterialTheme
                    .typography
                    .bodyMedium
        )

        Spacer(
            modifier =
                Modifier.height(32.dp)
        )

        //----------------------------------
        // Passenger Button
        //----------------------------------

        Button(

            onClick = {

                navController.navigate(
                    "signup/passenger"
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                "Continue as Passenger"
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        //----------------------------------
        // Driver Button
        //----------------------------------

        Button(

            onClick = {

                navController.navigate(
                    "signup/driver"
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        ) {

            Text(
                "Continue as Driver"
            )
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        //----------------------------------
        // Back to Login
        //----------------------------------

        TextButton(

            onClick = {

                navController.popBackStack()
            }
        ) {

            Text(
                "Already have an account? Login"
            )
        }
    }
}