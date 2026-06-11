package `in`.shrigo.app.screens.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import `in`.shrigo.app.models.SignupRequest
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.screens.rides.UploadRideViewModel
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.text.ClickableText
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignupScreen(

    navController: NavController,

    role: String,

    viewModel: UploadRideViewModel =
        viewModel()

) {

    val context =
        LocalContext.current

    //----------------------------------
    // Signup Type
    //----------------------------------

    val isPassenger =
        role == "passenger"

    val screenTitle =

        if (isPassenger)
            "Create Passenger Account"
        else
            "Create Driver Account"

    val screenSubtitle =

        if (isPassenger)
            "Find rides and travel easily"
        else
            "Offer rides and earn while travelling"

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
    // Terms & Subscription
    //----------------------------------

    var acceptedTerms by remember {
        mutableStateOf(false)
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedSubscription by remember {
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

                popUpTo(0) {
                    inclusive = true
                }

                launchSingleTop =
                    true
            }
        }
    }

    //----------------------------------
    // Error Toast
    //----------------------------------

    error?.let {

        LaunchedEffect(it) {

            Toast.makeText(
                context,
                it,
                Toast.LENGTH_LONG
            ).show()
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
            .padding(bottom = 100.dp)

    ) {

        //----------------------------------
        // Header
        //----------------------------------

        Text(

            text =
                screenTitle,

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
                screenSubtitle,

            style =
                MaterialTheme
                    .typography
                    .bodyMedium
        )

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
        // Driver Fields
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
        // Common Fields
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

        //----------------------------------
        // Password
        //----------------------------------

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

            value =
                confirmPassword,

            onValueChange = {
                confirmPassword = it
            },

            label = {
                Text(
                    "Confirm Password *"
                )
            },

            visualTransformation =
                PasswordVisualTransformation(),

            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        //----------------------------------
        // Driver Subscription
        //----------------------------------

        if (!isPassenger) {

            Text(
                text = "Please choose Subscription",
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {

                OutlinedTextField(

                    value = selectedSubscription,

                    onValueChange = {},

                    readOnly = true,

                    label = {
                        Text("Subscription")
                    },

                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    DropdownMenuItem(

                        text = {
                            Text(
                                "₹200 / Month - Driver Subscription"
                            )
                        },

                        onClick = {

                            selectedSubscription =
                                "₹200 / Month"

                            expanded = false
                        }
                    )
                }
            }

            Spacer(
                modifier =
                    Modifier.height(16.dp)
            )
        }

        //----------------------------------
        // Terms & Conditions
        //----------------------------------

        Row(
            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Checkbox(
                checked = acceptedTerms,
                onCheckedChange = {
                    acceptedTerms = it
                }
            )

            val annotatedText =
                buildAnnotatedString {

                    append("I agree to ")

                    pushStringAnnotation(
                        tag = "TERMS",
                        annotation = "https://shrigo-cmc6eaccfzfzfxdf.canadacentral-01.azurewebsites.net/Terms"
                    )

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration =
                                TextDecoration.Underline,
                            fontWeight =
                                FontWeight.Medium
                        )
                    ) {
                        append("Terms & Conditions")
                    }

                    pop()

                    append(" and ")

                    pushStringAnnotation(
                        tag = "PRIVACY",
                        annotation = "https://shrigo-cmc6eaccfzfzfxdf.canadacentral-01.azurewebsites.net/Privacy"
                    )

                    withStyle(
                        style = SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            textDecoration =
                                TextDecoration.Underline,
                            fontWeight =
                                FontWeight.Medium
                        )
                    ) {
                        append("Privacy Policy")
                    }

                    pop()
                }

            ClickableText(

                text = annotatedText,

                onClick = { offset ->

                    annotatedText
                        .getStringAnnotations(
                            start = offset,
                            end = offset
                        )
                        .firstOrNull()
                        ?.let { annotation ->

                            val intent =
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(
                                        annotation.item
                                    )
                                )

                            context.startActivity(
                                intent
                            )
                        }
                }
            )
        }

        //----------------------------------
        // Signup Button
        //----------------------------------

        Button(

            enabled =
                !isLoading,

            onClick = {

                //----------------------------------
                // Terms Validation
                //----------------------------------

                if (!acceptedTerms) {

                    Toast.makeText(
                        context,
                        "Please accept Terms & Conditions",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                //----------------------------------
                // Driver Subscription Validation
                //----------------------------------

                if (
                    !isPassenger
                    &&
                    selectedSubscription.isBlank()
                ) {

                    Toast.makeText(
                        context,
                        "Please choose subscription",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@Button
                }

                //----------------------------------
                // Validation
                //----------------------------------

                if (

                    firstName
                        .isBlank()

                    ||

                    contact
                        .isBlank()

                    ||

                    password
                        .isBlank()

                ) {

                    Toast.makeText(

                        context,

                        "Please fill mandatory fields (*)",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }
                if (password.length < 8) {

                    Toast.makeText(

                        context,

                        "Password must be at least 8 characters",

                        Toast.LENGTH_SHORT

                    ).show()

                    return@Button
                }
                if (

                    !isPassenger
                    &&
                    (
                            aadharNumber
                                .isBlank()

                                    ||
                                    vehicleRegNo
                                        .isBlank()
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
                // Signup Request
                //----------------------------------

                val request =

                    if (isPassenger) {

                        SignupRequest(

                            passengerFirstName =
                                firstName.trim(),

                            passengerLastName =
                                lastName.trim(),

                            passengerAge =
                                age.trim(),

                            passengerEmail =
                                email.trim(),

                            passengerContact =
                                contact.trim(),

                            passengerPswd =
                                password.trim(),

                            passengerRole =
                                "Passenger",

                            acceptedTerms =
                                acceptedTerms
                        )

                    } else {

                        SignupRequest(

                            userFirstName =
                                firstName.trim(),

                            userLastName =
                                lastName.trim(),

                            userAge =
                                age.trim(),

                            userEmail =
                                email.trim(),

                            userContact =
                                contact.trim(),

                            userPswd =
                                password.trim(),

                            userRole =
                                "Driver",

                            subscription =
                                selectedSubscription,

                            vehicleRegNo =
                                vehicleRegNo.trim(),

                            vehicleInsur =
                                insuranceNo.trim(),

                            vehicleModel =
                                vehicleName.trim(),

                            acceptedTerms =
                                acceptedTerms
                        )
                    }

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

        //----------------------------------
        // Login
        //----------------------------------

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

        //----------------------------------
        // Error Text
        //----------------------------------

        error?.let {

            Spacer(
                modifier =
                    Modifier.height(12.dp)
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