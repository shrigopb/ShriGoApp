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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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