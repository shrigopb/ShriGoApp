package `in`.shrigo.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import `in`.shrigo.app.screens.home.HomeScreen
import `in`.shrigo.app.screens.splash.SplashScreen

@Composable
fun AppNavHost() {

    val navController =
        rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        composable("splash") {
            SplashScreen(navController)
        }

        composable("home") {
            HomeScreen()
        }
    }
}