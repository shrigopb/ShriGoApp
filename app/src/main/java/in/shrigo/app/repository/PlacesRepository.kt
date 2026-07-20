package `in`.shrigo.app.repository


import android.content.Context
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import `in`.shrigo.app.models.PlaceSuggestion
import `in`.shrigo.app.services.GooglePlacesService
import kotlinx.coroutines.tasks.await

class PlacesRepository(context: Context) {

    private val placesClient: PlacesClient =
        GooglePlacesService.getClient(context)

    suspend fun searchPlaces(query: String): List<PlaceSuggestion> {

        if (query.isBlank()) {
            return emptyList()
        }

        val token = AutocompleteSessionToken.newInstance()

        val request = FindAutocompletePredictionsRequest.builder()
            .setSessionToken(token)
            .setQuery(query)
            .build()

        val response =
            placesClient
                .findAutocompletePredictions(request)
                .await()

        return response.autocompletePredictions.map {

            PlaceSuggestion(
                placeId = it.placeId,
                primaryText = it.getPrimaryText(null).toString(),
                secondaryText = it.getSecondaryText(null).toString()
            )
        }
    }
}