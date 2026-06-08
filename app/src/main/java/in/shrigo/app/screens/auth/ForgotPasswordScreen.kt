package `in`.shrigo.app.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.navigation.Routes

@Composable
fun ForgotPasswordScreen(

    navController:
    NavController,

    viewModel:
    LoginViewModel =
        viewModel()

) {

    var email by remember {

        mutableStateOf("")
    }

    val context =
        androidx.compose.ui.platform
            .LocalContext.current

    val isLoading by
    viewModel.isLoading
        .collectAsState()

    val success by
    viewModel
        .forgotPasswordSuccess
        .collectAsState()

    val message by
    viewModel
        .forgotPasswordMessage
        .collectAsState()

    LaunchedEffect(success) {

        if (success) {

            Toast.makeText(

                context,

                message
                    ?: "Reset link sent",

                Toast.LENGTH_LONG

            ).show()

            navController.navigate(
                Routes.LOGIN
            ) {

                launchSingleTop =
                    true
            }
        }
    }

    Scaffold { padding ->

        Column(

            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Text(

                text =
                    "Forgot Password",

                style =
                    MaterialTheme
                        .typography
                        .headlineMedium
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Text(

                text =
                    "Enter your registered email address"
            )

            Spacer(
                modifier =
                    Modifier.height(30.dp)
            )

            OutlinedTextField(

                value = email,

                onValueChange = {

                    email = it
                },

                label = {

                    Text(
                        "Email Address"
                    )
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier =
                    Modifier.height(20.dp)
            )

            Button(

                onClick = {

                    if (
                        email.isBlank()
                    ) {

                        Toast.makeText(

                            context,

                            "Please enter email",

                            Toast.LENGTH_SHORT

                        ).show()

                    } else {

                        viewModel
                            .forgotPassword(
                                email.trim()
                            )
                    }
                },

                modifier =
                    Modifier.fillMaxWidth()
            ) {

                Text(

                    if (isLoading)
                        "Sending..."
                    else
                        "Send Reset Link"
                )
            }

            message?.let {

                Spacer(
                    modifier =
                        Modifier.height(12.dp)
                )

                Text(
                    text = it
                )
            }
        }
    }
}