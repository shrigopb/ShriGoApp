package `in`.shrigo.app.screens.notifications


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import `in`.shrigo.app.utils.SessionManager

@Composable
fun NotificationScreen(

    sessionManager: SessionManager

) {

    val viewModel:
            NotificationViewModel =
        viewModel()

    val uniqueId =
        sessionManager.getUserUniqueId()

    LaunchedEffect(Unit) {

        viewModel.loadNotifications(
            uniqueId
        )
    }

    LazyColumn {

        items(
            viewModel.notifications
        ) { notification ->

            Card(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
            ) {

                Column(
                    modifier =
                        Modifier.padding(
                            16.dp
                        )
                ) {

                    Text(
                        text =
                            notification.title
                                ?: ""
                    )

                    Spacer(
                        Modifier.height(
                            4.dp
                        )
                    )

                    Text(
                        text =
                            notification.message
                                ?: ""
                    )

                    Spacer(
                        Modifier.height(
                            4.dp
                        )
                    )

                    Text(
                        text =
                            notification.createdDate
                                ?: ""
                    )
                }
            }
        }
    }
}