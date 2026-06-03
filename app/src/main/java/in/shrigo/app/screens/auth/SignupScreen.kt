package `in`.shrigo.app.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.models.SignupRequest
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.screens.rides.UploadRideViewModel

@Composable
fun SignupScreen(

    navController:
    NavController,

    viewModel:
    UploadRideViewModel =
        viewModel()

) {

    val context =
        LocalContext.current

    //----------------------------------
    // Form States
    //----------------------------------

    var firstName by remember {
        mutableStateOf("")
    }

    var lastName by remember {
        mutableStateOf("")
    }

    var age by remember {
        mutableStateOf("")
    }

    var email by remember {
        mutableStateOf("")
    }

    var contact by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    var confirmPassword by remember {
        mutableStateOf("")
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

            Toast.makeText(

                context,

                "Signup Successful",

                Toast.LENGTH_SHORT

            ).show()

            navController.navigate(
                Routes.LOGIN
            ) {

                popUpTo(
                    Routes.SIGNUP
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
            .verticalScroll(
                rememberScrollState()
            )

    ) {

        Text(

            text =
                "Create Account",

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
        // First Name
        //----------------------------------

        OutlinedTextField(

            value =
                firstName,

            onValueChange = {

                firstName = it
            },

            label = {
                Text(
                    "First Name"
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Last Name
        //----------------------------------

        OutlinedTextField(

            value =
                lastName,

            onValueChange = {

                lastName = it
            },

            label = {
                Text(
                    "Last Name"
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Age
        //----------------------------------

        OutlinedTextField(

            value =
                age,

            onValueChange = {

                age = it
            },

            label = {
                Text("Age")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Email
        //----------------------------------

        OutlinedTextField(

            value =
                email,

            onValueChange = {

                email = it
            },

            label = {
                Text("Email")
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Contact
        //----------------------------------

        OutlinedTextField(

            value =
                contact,

            onValueChange = {

                contact = it
            },

            label = {
                Text(
                    "Mobile Number"
                )
            },

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Password
        //----------------------------------

        OutlinedTextField(

            value =
                password,

            onValueChange = {

                password = it
            },

            label = {
                Text(
                    "Password"
                )
            },

            visualTransformation =
                PasswordVisualTransformation(),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        //----------------------------------
        // Confirm Password
        //----------------------------------

        OutlinedTextField(

            value =
                confirmPassword,

            onValueChange = {

                confirmPassword = it
            },

            label = {
                Text(
                    "Confirm Password"
                )
            },

            visualTransformation =
                PasswordVisualTransformation(),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        //----------------------------------
        // Signup Button
        //----------------------------------

        Button(

            onClick = {

                if (
                    firstName.isBlank()
                    ||
                    contact.isBlank()
                    ||
                    password.isBlank()
                ) {

                    Toast.makeText(

                        context,

                        "Please fill mandatory fields",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }

                if (
                    password
                    !=
                    confirmPassword
                ) {

                    Toast.makeText(

                        context,

                        "Passwords do not match",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }

                val request =

                    SignupRequest(

                        passengerFirstName =
                            firstName,

                        passengerLastName =
                            lastName,

                        passengerAge =
                            age,

                        passengerEmail =
                            email,

                        passengerContact =
                            contact,

                        passengerPswd =
                            password
                    )

                viewModel
                    .signupUser(
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

                    "Creating Account..."

                else

                    "Sign Up"
            )
        }

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        TextButton(

            onClick = {

                navController
                    .navigate(
                        Routes.LOGIN
                    )
            }

        ) {

            Text(

                "Already have an account? Login"
            )
        }

        error?.let {

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
