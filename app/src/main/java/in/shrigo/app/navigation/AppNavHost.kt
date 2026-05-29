package `in`.shrigo.app.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.*
import `in`.shrigo.app.screens.bookings.BookingScreen
import `in`.shrigo.app.screens.home.HomeScreen
import `in`.shrigo.app.screens.profile.ProfileScreen
import `in`.shrigo.app.screens.rides.MyRidesScreen
import `in`.shrigo.app.screens.splash.SplashScreen
import `in`.shrigo.app.screens.auth.LoginScreen
@Composable
fun AppNavHost() {

    val navController =
        rememberNavController()

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
            ) {

                NavigationBar(
                    containerColor = Color.White
                ) {

                    bottomNavItems.forEach { item ->

                        NavigationBarItem(

                            selected =
                                currentRoute
                                    ?.startsWith(
                                        item.route
                                    ) == true,

                            onClick = {

                                when(item.route) {

                                    Routes.MY_RIDES,
                                    Routes.BOOKINGS,
                                    Routes.PROFILE -> {

                                        navController.navigate(
                                            Routes.LOGIN
                                        )
                                    }

                                    else -> {

                                        navController.navigate(
                                            item.route
                                        ) {
                                            popUpTo(
                                                navController.graph.startDestinationId
                                            )

                                            launchSingleTop =
                                                true
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

            // Home with firstName
            composable(

                "${Routes.HOME}/{firstName}"

            ) { backStackEntry ->

                val firstName =
                    backStackEntry
                        .arguments
                        ?.getString(
                            "firstName"
                        ) ?: ""

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

                MyRidesScreen()
            }

            // Bookings
            composable(
                Routes.BOOKINGS
            ) {

                BookingScreen()
            }

            // Profile
            composable(
                Routes.PROFILE
            ) {

                ProfileScreen()
            }
        }
    }
}