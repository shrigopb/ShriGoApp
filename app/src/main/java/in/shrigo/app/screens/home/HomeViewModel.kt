package `in`.shrigo.app.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import `in`.shrigo.app.models.Ride
import `in`.shrigo.app.repository.NotificationRepository
import `in`.shrigo.app.repository.RideRepository

class HomeViewModel : ViewModel() {

    private val repository = RideRepository()
    private val notificationrepository = NotificationRepository()
    private val _rides =
        MutableStateFlow<List<Ride>>(emptyList())
    val rides: StateFlow<List<Ride>>
            = _rides

    private val _isLoading =
        MutableStateFlow(false)
    val isLoading: StateFlow<Boolean>
            = _isLoading

    private val _error =
        MutableStateFlow<String?>(null)
    val error: StateFlow<String?>
            = _error

    init {
        fetchRides()
    }

    private fun fetchRides() {

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val response =
                    repository.getRides()

                _rides.value = response

            } catch (e: Exception) {

                _error.value =
                    e.message

            } finally {

                _isLoading.value = false
            }
        }
    }

    private val _notificationCount =
        MutableStateFlow(0)

    val notificationCount:
            StateFlow<Int> =
        _notificationCount

    fun loadNotificationCount(
        uniqueId: String
    ) {

        viewModelScope.launch {

            try {

                val count =
                    notificationrepository
                        .getNotificationCount(
                            uniqueId
                        )

                _notificationCount.value =
                    count

            } catch (e: Exception) {

                Log.e(
                    "NOTIFICATION",
                    "Count Error",
                    e
                )
            }
        }
    }
}