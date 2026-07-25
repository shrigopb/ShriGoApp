package `in`.shrigo.app.screens.favorite


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import `in`.shrigo.app.utils.SessionManager

@Composable
fun FavoriteRouteScreen(

    sessionManager: SessionManager,

    viewModel: FavoriteRouteViewModel = androidx.lifecycle.viewmodel.compose.viewModel()

) {

    val favorites by viewModel.favorites.collectAsState()

    val isLoading by viewModel.isLoading.collectAsState()

    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {

        viewModel.loadFavorites(

            sessionManager.getUserUniqueId()

        )
    }

    when {

        isLoading -> {

            Column(

                modifier = Modifier.fillMaxSize(),

                verticalArrangement = Arrangement.Center

            ) {

                CircularProgressIndicator()
            }
        }

        error != null -> {

            Text(

                text = error ?: "",

                color = MaterialTheme.colorScheme.error,

                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {

            LazyColumn(

                modifier = Modifier.fillMaxSize(),

                contentPadding = PaddingValues(16.dp),

                verticalArrangement = Arrangement.spacedBy(12.dp)

            ) {

                items(favorites) { favorite ->

                    FavoriteRouteCard(

                        favorite = favorite,

                        onUpload = {

                        },

                        onEdit = {

                        },

                        onDelete = {

                        }
                    )
                }
            }
        }
    }
}