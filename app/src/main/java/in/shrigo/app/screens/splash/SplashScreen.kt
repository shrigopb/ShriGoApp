package `in`.shrigo.app.screens.splash


import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import `in`.shrigo.app.R
import `in`.shrigo.app.navigation.Routes
import `in`.shrigo.app.utils.SessionManager
import kotlinx.coroutines.delay
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun SplashScreen(
    navController: NavController
) {

    val context =
        LocalContext.current

    val splashViewModel: SplashViewModel = viewModel()

    val showUpdateDialog by
    splashViewModel.showUpdateDialog.collectAsState()

    val versionResponse by
    splashViewModel.versionResponse.collectAsState()

    val startupCompleted by
    splashViewModel.startupCompleted.collectAsState()

    val sessionManager =
        remember {
            SessionManager(
                context
            )
        }

    fun navigateToHome() {

        val firstName =
            if (sessionManager.isLoggedIn()) {
                sessionManager.getFirstName() ?: "Guest"
            } else {
                "Guest"
            }

        navController.navigate("${Routes.HOME}/$firstName") {

            popUpTo(Routes.SPLASH) {
                inclusive = true
            }

            launchSingleTop = true
        }
    }
    var startAnimation by remember {
        mutableStateOf(false)
    }

    val scaleAnimation by animateFloatAsState(

        targetValue =
            if (startAnimation)
                1f
            else
                0.8f,

        animationSpec = tween(
            durationMillis = 1200,
            easing =
                FastOutSlowInEasing
        ),

        label = ""
    )

    val alphaAnimation by animateFloatAsState(

        targetValue =
            if (startAnimation)
                1f
            else
                0f,

        animationSpec = tween(
            durationMillis = 1500
        ),

        label = ""
    )

    val versionName =
        context.packageManager
            .getPackageInfo(
                context.packageName,
                0
            ).versionName ?: "0.0.0"

    LaunchedEffect(Unit) {

        startAnimation = true

        splashViewModel.checkLatestVersion(versionName)
    }

    LaunchedEffect(startupCompleted) {

        if (startupCompleted) {

            delay(2500)

            if (!showUpdateDialog) {
                navigateToHome()
            }
        }
    }

    Box(

        modifier = Modifier
            .fillMaxSize()
            .background(

                brush =
                    Brush.verticalGradient(

                        colors = listOf(

                            Color(
                                0xFF0F172A
                            ),

                            Color(
                                0xFF1E293B
                            ),

                            Color(
                                0xFF2E7D32
                            )
                        )
                    )
            ),

        contentAlignment =
            Alignment.Center
    ) {

        Column(

            horizontalAlignment =
                Alignment.CenterHorizontally,

            verticalArrangement =
                Arrangement.Center,

            modifier =
                Modifier
                    .scale(
                        scaleAnimation
                    )
                    .alpha(
                        alphaAnimation
                    )
        ) {

            Image(

                painter =
                    painterResource(
                        id =
                            R.drawable
                                .shrigo_logo
                    ),

                contentDescription =
                    "ShriGo Logo",

                modifier =
                    Modifier
                        .size(
                            260.dp
                        ),

                contentScale =
                    ContentScale.Fit
            )

            Spacer(
                modifier =
                    Modifier.height(
                        20.dp
                    )
            )

            Text(

                text =
                    "Premium Car Service",

                color =
                    Color.White,

                fontSize =
                    20.sp,

                fontWeight =
                    FontWeight
                        .SemiBold
            )

            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )

            Text(

                text =
                    "Ride Smart. Ride ShriGo.",

                color =
                    Color.LightGray,

                style =
                    MaterialTheme
                        .typography
                        .bodyMedium
            )
            Spacer(
                modifier =
                    Modifier.height(
                        10.dp
                    )
            )


            Text(
                text = "Version ${versionName ?: "0.0.0"}",
                fontSize = 18.sp,
                color = Color.LightGray.copy(alpha = 0.7f)
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "A Product of ShriAITech",
                fontSize = 12.sp,
                color = Color.LightGray.copy(alpha = 0.7f)
            )
        }
        if (showUpdateDialog && versionResponse != null) {

            androidx.compose.material3.AlertDialog(

                onDismissRequest = {
                    // Prevent dismiss by tapping outside
                },

                title = {
                    Text(
                        if (versionResponse!!.forceUpdate)
                            "Update Required"
                        else
                            "Update Available"
                    )
                },

                text = {
                    Text(versionResponse!!.message)
                },

                confirmButton = {

                    androidx.compose.material3.TextButton(

                        onClick = {

                            val intent = android.content.Intent(
                                android.content.Intent.ACTION_VIEW,
                                android.net.Uri.parse(
                                    versionResponse!!.playStoreUrl
                                )
                            )

                            context.startActivity(intent)
                        }

                    ) {

                        Text("Update Now")
                    }
                },

                dismissButton = {

                    if (!versionResponse!!.forceUpdate) {

                        androidx.compose.material3.TextButton(

                            onClick = {
                                navigateToHome()
                            }

                        ) {

                            Text("Later")
                        }
                    }
                }
            )
        }
    }
}
