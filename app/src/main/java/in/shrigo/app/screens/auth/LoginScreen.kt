package `in`.shrigo.app.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.navigation.Routes

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {

    var emailOrPhone by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val loginState by
    viewModel.loginState.collectAsState()

    val isLoading by
    viewModel.isLoading.collectAsState()

    val error by
    viewModel.error.collectAsState()

    // Navigate after successful login
    LaunchedEffect(loginState) {

        loginState?.let {

            navController.navigate(

                "${Routes.HOME}/${it.firstName}"

            ) {

                popUpTo(
                    Routes.LOGIN
                ) {

                    inclusive = true
                }

                launchSingleTop = true
            }
        }
    }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center
        ) {

            Text(
                text = "Welcome to ShriGo",
                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,

                fontWeight =
                    FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(10.dp)
            )

            Text(
                text =
                    "Login to continue"
            )

            Spacer(
                modifier =
                    Modifier.height(30.dp)
            )

            // Email / Phone
            OutlinedTextField(
                value = emailOrPhone,

                onValueChange = {
                    emailOrPhone = it
                },

                label = {
                    Text("Email / Phone")
                },

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )

            // Password
            OutlinedTextField(
                value = password,

                onValueChange = {
                    password = it
                },

                label = {
                    Text("Password")
                },

                visualTransformation =
                    PasswordVisualTransformation(),

                modifier =
                    Modifier.fillMaxWidth(),

                singleLine = true
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            // Login Button
            Button(
                onClick = {

                    if (
                        emailOrPhone.isNotBlank()
                        &&
                        password.isNotBlank()
                    ) {

                        viewModel.loginUser(
                            emailOrPhone.trim(),
                            password.trim()
                        )
                    }
                },

                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(14.dp)
            ) {

                if (isLoading) {

                    CircularProgressIndicator()

                } else {

                    Text("Login")
                }
            }

            // Error message
            error?.let {

                Spacer(
                    modifier =
                        Modifier.height(10.dp)
                )

                Text(
                    text = it,
                    color =
                        MaterialTheme
                            .colorScheme
                            .error
                )
            }
        }
    }
}