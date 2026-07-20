package `in`.shrigo.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import `in`.shrigo.app.navigation.AppNavHost
import `in`.shrigo.app.ui.theme.ShriGoAppTheme
import com.google.android.libraries.places.api.Places
import `in`.shrigo.app.BuildConfig
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            // Initialize Google Places
            if (!Places.isInitialized()) {
                Places.initialize(
                    applicationContext,
                    BuildConfig.MAPS_API_KEY
                )
            }

            android.util.Log.d(
                "GOOGLE_MAPS",
                BuildConfig.MAPS_API_KEY
            )


        enableEdgeToEdge()

        setContent {

            ShriGoAppTheme {

                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    AppNavHost()
                }
            }
        }
    }
}