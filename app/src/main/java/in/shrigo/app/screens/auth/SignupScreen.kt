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

    navController: NavController,

    viewModel: UploadRideViewModel =
        viewModel()

) {

    val context = LocalContext.current

    //----------------------------------
    // Signup Type
    //----------------------------------

    var isPassenger by remember {
        mutableStateOf(true)
    }

    //----------------------------------
    // Common Fields
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
    // Driver Fields
    //----------------------------------

    var aadharNumber by remember {
        mutableStateOf("")
    }

    var vehicleRegNo by remember {
        mutableStateOf("")
    }

    var insuranceNo by remember {
        mutableStateOf("")
    }

    var vehicleName by remember {
        mutableStateOf("")
    }

    //----------------------------------
    // ViewModel State
    //----------------------------------

    val isLoading by
    viewModel.isLoading.collectAsState()

    val uploadSuccess by
    viewModel.uploadSuccess.collectAsState()

    val error by
    viewModel.error.collectAsState()

    //----------------------------------
    // Success Navigation
    //----------------------------------

    LaunchedEffect(uploadSuccess) {

        if (uploadSuccess) {

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
            .verticalScroll(
                rememberScrollState()
            )
            .padding(16.dp)

    ) {

        Text(

            text = "Create Account",

            style =
                MaterialTheme
                    .typography
                    .headlineMedium
        )

        Spacer(
            modifier =
                Modifier.height(16.dp)
        )

        //----------------------------------
        // Passenger / Driver Toggle
        //----------------------------------

//----------------------------------
// Passenger / Driver Toggle
//----------------------------------

        Row(

            horizontalArrangement =
                Arrangement.spacedBy(12.dp)

        ) {

            // Passenger Button
            if (isPassenger) {

                Button(

                    onClick = {
                        isPassenger = true
                    }

                ) {

                    Text("Passenger")
                }

            } else {

                OutlinedButton(

                    onClick = {
                        isPassenger = true
                    }

                ) {

                    Text("Passenger")
                }
            }

            // Driver Button
            if (!isPassenger) {

                Button(

                    onClick = {
                        isPassenger = false
                    }

                ) {

                    Text("Driver")
                }

            } else {

                OutlinedButton(

                    onClick = {
                        isPassenger = false
                    }

                ) {

                    Text("Driver")
                }
            }
        }

        Spacer(
            modifier =
                Modifier.height(24.dp)
        )

        //----------------------------------
        // Common Fields
        //----------------------------------

        AppTextField(
            value = firstName,
            onValueChange = {
                firstName = it
            },
            label = "First Name *"
        )

        AppTextField(
            value = lastName,
            onValueChange = {
                lastName = it
            },
            label = "Last Name"
        )

        AppTextField(
            value = age,
            onValueChange = {
                age = it
            },
            label = "Age"
        )

        //----------------------------------
        // Driver Only Fields
        //----------------------------------

        if (!isPassenger) {

            AppTextField(
                value = aadharNumber,
                onValueChange = {
                    aadharNumber = it
                },
                label = "Aadhar Number *"
            )

            AppTextField(
                value = vehicleRegNo,
                onValueChange = {
                    vehicleRegNo = it
                },
                label = "Vehicle Reg No *"
            )

            AppTextField(
                value = insuranceNo,
                onValueChange = {
                    insuranceNo = it
                },
                label = "Insurance No"
            )

            AppTextField(
                value = vehicleName,
                onValueChange = {
                    vehicleName = it
                },
                label = "Vehicle Name"
            )
        }

        //----------------------------------
        // Remaining Common Fields
        //----------------------------------

        AppTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = "Email"
        )

        AppTextField(
            value = contact,
            onValueChange = {
                contact = it
            },
            label = "Mobile Number *"
        )

        OutlinedTextField(

            value = password,

            onValueChange = {
                password = it
            },

            label = {
                Text("Password *")
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

        OutlinedTextField(

            value = confirmPassword,

            onValueChange = {
                confirmPassword = it
            },

            label = {
                Text("Confirm Password *")
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

                //----------------------------------
                // Validation
                //----------------------------------

                if (
                    firstName.isBlank() ||
                    contact.isBlank() ||
                    password.isBlank()
                ) {

                    Toast.makeText(

                        context,

                        "Please fill mandatory fields (*)",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }

                if (

                    !isPassenger &&
                    (
                            aadharNumber.isBlank() ||
                                    vehicleRegNo.isBlank()
                            )

                ) {

                    Toast.makeText(

                        context,

                        "Please fill Driver mandatory fields",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }

                if (
                    password !=
                    confirmPassword
                ) {

                    Toast.makeText(

                        context,

                        "Passwords do not match",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }

                //----------------------------------
                // Passenger Signup
                //----------------------------------

                val request = SignupRequest(

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

                viewModel.signupUser(
                    request
                )
            },

            modifier =
                Modifier.fillMaxWidth()

        ) {

            Text(

                if (isLoading)
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

                navController.navigate(
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

            Text(text = it)
        }
    }
}

@Composable
fun AppTextField(

    value: String,

    onValueChange:
        (String) -> Unit,

    label: String

) {

    OutlinedTextField(

        value = value,

        onValueChange =
            onValueChange,

        label = {
            Text(label)
        },

        modifier =
            Modifier.fillMaxWidth()
    )

    Spacer(
        modifier =
            Modifier.height(12.dp)
    )
}