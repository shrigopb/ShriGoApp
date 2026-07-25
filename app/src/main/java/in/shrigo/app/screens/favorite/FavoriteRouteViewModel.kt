package `in`.shrigo.app.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.FavoriteRoute
import `in`.shrigo.app.repository.FavoriteRouteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FavoriteRouteViewModel : ViewModel() {

    private val repository = FavoriteRouteRepository()

    //--------------------------------------------------
    // Favorite Routes
    //--------------------------------------------------

    private val _favorites =
        MutableStateFlow<List<FavoriteRoute>>(emptyList())

    val favorites: StateFlow<List<FavoriteRoute>> =
        _favorites

    //--------------------------------------------------
    // Loading
    //--------------------------------------------------

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading

    //--------------------------------------------------
    // Error
    //--------------------------------------------------

    private val _error =
        MutableStateFlow<String?>(null)

    val error: StateFlow<String?> =
        _error

    //--------------------------------------------------
    // Load Favorites
    //--------------------------------------------------

    fun loadFavorites(driverUniqueId: String) {

        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            try {

                val response =
                    repository.getFavorites(driverUniqueId)

                if (response.isSuccessful) {

                    _favorites.value =
                        response.body() ?: emptyList()

                } else {

                    _error.value =
                        response.message()
                }

            } catch (e: Exception) {

                _error.value =
                    e.message
            }

            _isLoading.value = false
        }
    }
}