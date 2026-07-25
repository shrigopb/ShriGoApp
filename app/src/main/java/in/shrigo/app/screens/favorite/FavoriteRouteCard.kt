package `in`.shrigo.app.screens.favorite

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import `in`.shrigo.app.models.FavoriteRoute

@Composable
fun FavoriteRouteCard(

    favorite: FavoriteRoute,

    onUpload: () -> Unit,

    onEdit: () -> Unit,

    onDelete: () -> Unit

) {

    Card(

        modifier = Modifier.fillMaxWidth(),

        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )

    ) {

        Column(

            modifier = Modifier.padding(16.dp)

        ) {

            Text(

                text = favorite.favoriteName ?: favorite.routeName,

                style = MaterialTheme.typography.titleMedium,

                fontWeight = FontWeight.Bold

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(

                buildAnnotatedString {

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("From : ")
                    }
                    append(favorite.rideFrom)

                    append("\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Via : ")
                    }
                    append(favorite.rideVia)

                    append("\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("To : ")
                    }
                    append(favorite.rideTo)

                    append("\n\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Time : ")
                    }
                    append(favorite.rideTime)

                    append("\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Seats : ")
                    }
                    append(favorite.rideSeats.toString())

                    append("\n")

                    withStyle(
                        SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) {
                        append("Price : ")
                    }
                    append("₹${favorite.ridePrice}")
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(

                onClick = onUpload,

                modifier = Modifier.fillMaxWidth()

            ) {

                Icon(

                    Icons.Default.DirectionsCar,

                    contentDescription = null

                )

                Spacer(modifier = Modifier.width(8.dp))

                Text("Upload Ride")

            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(

                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement = Arrangement.SpaceBetween,

                verticalAlignment = Alignment.CenterVertically

            ) {

                OutlinedButton(

                    onClick = onEdit

                ) {

                    Text("Edit")

                }

                OutlinedButton(

                    onClick = onDelete

                ) {

                    Text("Delete")

                }
            }
        }
    }
}