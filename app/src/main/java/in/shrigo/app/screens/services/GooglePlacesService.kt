package `in`.shrigo.app.services



import android.content.Context
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.net.PlacesClient

object GooglePlacesService {

    private var placesClient: PlacesClient? = null

    fun getClient(context: Context): PlacesClient {

        if (placesClient == null) {
            placesClient = Places.createClient(context)
        }

        return placesClient!!
    }
}