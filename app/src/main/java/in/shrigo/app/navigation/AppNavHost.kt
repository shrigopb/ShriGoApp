package `in`.shrigo.app.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import `in`.shrigo.app.screens.auth.LoginScreen
import `in`.shrigo.app.screens.auth.SignupScreen
import `in`.shrigo.app.screens.bookings.BookingScreen
import `in`.shrigo.app.screens.home.HomeScreen
import `in`.shrigo.app.screens.rides.MyRidesScreen
import `in`.shrigo.app.screens.profile.ProfileScreen
import `in`.shrigo.app.screens.splash.SplashScreen
import `in`.shrigo.app.utils.SessionManager
import `in`.shrigo.app.screens.rides.UploadRideScreen
import `in`.shrigo.app.screens.rides.EditRideScreen

@Composable
fun AppNavHost() {

    val navController =
        rememberNavController()

    val context =
        LocalContext.current

    val sessionManager =
        remember {
            SessionManager(
                context
            )
        }

    val bottomNavItems = listOf(
        BottomNav.Home,
        BottomNav.MyRides,
        BottomNav.Bookings,
        BottomNav.Profile
    )

    val currentRoute =
        navController
            .currentBackStackEntryAsState()
            .value
            ?.destination
            ?.route

    Scaffold(

        bottomBar = {

            if (

                currentRoute != Routes.SPLASH
                &&
                currentRoute != Routes.LOGIN
                &&
                currentRoute != Routes.SIGNUP
            ) {

                NavigationBar(
                    containerColor =
                        Color.White
                ) {

                    bottomNavItems.forEach { item ->

                        NavigationBarItem(

                            selected =
                                currentRoute
                                    ?.startsWith(
                                        item.route
                                    ) == true,

                            onClick = {

                                when (
                                    item.route
                                ) {

                                    Routes.MY_RIDES,
                                    Routes.BOOKINGS,
                                    Routes.PROFILE -> {

                                        if (
                                            sessionManager
                                                .isLoggedIn()
                                        ) {

                                            navController
                                                .navigate(
                                                    item.route
                                                ) {

                                                    launchSingleTop =
                                                        true
                                                }

                                        } else {

                                            navController
                                                .navigate(
                                                    Routes.LOGIN
                                                )
                                        }
                                    }

                                    Routes.HOME -> {

                                        val firstName =

                                            if (
                                                sessionManager
                                                    .isLoggedIn()
                                            ) {

                                                sessionManager
                                                    .getFirstName()
                                                    ?: "Guest"

                                            } else {

                                                "Guest"
                                            }

                                        navController.navigate(

                                            "${Routes.HOME}/$firstName"

                                        ) {

                                            launchSingleTop =
                                                true

                                            restoreState =
                                                true

                                            popUpTo(
                                                navController
                                                    .graph
                                                    .startDestinationId
                                            ) {

                                                saveState =
                                                    true
                                            }
                                        }
                                    }
                                }
                            },

                            icon = {

                                Icon(
                                    imageVector =
                                        item.icon,

                                    contentDescription =
                                        item.title
                                )
                            },

                            label = {

                                Text(
                                    item.title
                                )
                            }
                        )
                    }
                }
            }
        }

    ) { _ ->

        NavHost(

            navController =
                navController,

            startDestination =
                Routes.SPLASH

        ) {

            // Splash
            composable(
                Routes.SPLASH
            ) {

                SplashScreen(
                    navController
                )
            }

            // Home
            composable(

                "${Routes.HOME}/{firstName}"

            ) { backStackEntry ->

                val firstName =

                    backStackEntry
                        .arguments
                        ?.getString(
                            "firstName"
                        )
                        ?: "Guest"

                HomeScreen(
                    firstName =
                        firstName
                )
            }

            // Login
            composable(
                Routes.LOGIN
            ) {

                LoginScreen(
                    navController
                )
            }

            // My Rides
            composable(
                Routes.MY_RIDES
            ) {

                if (
                    sessionManager
                        .isLoggedIn()
                ) {

                    MyRidesScreen(
                        navController
                    )

                } else {

                    LaunchedEffect(
                        Unit
                    ) {

                        navController
                            .navigate(
                                Routes.LOGIN
                            )
                    }
                }
            }
            //---------------------------
            //Signup
            //--------------------------
            // Signup
            composable(
                Routes.SIGNUP
            ) {

                SignupScreen(
                    navController
                )
            }
            //---------------------------
            //UPLOAD_RIDE
            //---------------------------
            composable(
                Routes.UPLOAD_RIDE
            ) {

                UploadRideScreen(
                    navController
                )
            }
            composable(
                Routes.EDIT_RIDE
            ) {

                EditRideScreen(
                    navController =
                        navController
                )
            }

            // Bookings
            composable(
                Routes.BOOKINGS
            ) {

                if (
                    sessionManager
                        .isLoggedIn()
                ) {

                    BookingScreen()

                } else {

                    LaunchedEffect(
                        Unit
                    ) {

                        navController
                            .navigate(
                                Routes.LOGIN
                            )
                    }
                }
            }

            // Profile
            composable(
                Routes.PROFILE
            ) {

                if (
                    sessionManager
                        .isLoggedIn()
                ) {

                    ProfileScreen(
                        navController
                    )

                } else {

                    LaunchedEffect(
                        Unit
                    ) {

                        navController
                            .navigate(
                                Routes.LOGIN
                            )
                    }
                }
            }
        }
    }
}