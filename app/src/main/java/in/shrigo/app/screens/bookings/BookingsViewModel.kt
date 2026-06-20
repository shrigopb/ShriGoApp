package `in`.shrigo.app.screens.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `in`.shrigo.app.models.BookingResponse
import `in`.shrigo.app.repository.BookingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookingsViewModel
    : ViewModel() {

    private val repository =

        BookingsRepository()

    //-----------------------------------
    // Bookings
    //-----------------------------------

    private val _bookings =

        MutableStateFlow<
                List<BookingResponse>
                >(emptyList())

    val bookings:
            StateFlow<
                    List<BookingResponse>
                    > = _bookings

    //-----------------------------------
    // Loading
    //-----------------------------------

    private val _isLoading =

        MutableStateFlow(
            false
        )

    val isLoading:
            StateFlow<Boolean> =
        _isLoading

    //-----------------------------------
    // Error
    //-----------------------------------

    private val _error =

        MutableStateFlow<
                String?
                >(null)

    val error:
            StateFlow<
                    String?
                    > = _error

    //-----------------------------------
    // Load My Bookings
    //-----------------------------------

    fun loadMyBookings(

        uniqueId:
        String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _error.value =
                null

            try {

                val response =

                    repository
                        .getMyBookings(
                            uniqueId
                        )

                _bookings.value =
                    response

            } catch (
                e: Exception
            ) {

                _error.value =
                    e.message
            }

            _isLoading.value =
                false
        }
    }

    //-----------------------------------
    // Load Driver Bookings
    //-----------------------------------

    fun loadDriverBookings(

        uniqueId:
        String

    ) {

        viewModelScope.launch {

            _isLoading.value =
                true

            _error.value =
                null

            try {

                val response =

                    repository
                        .getDriverBookings(
                            uniqueId
                        )

                _bookings.value =
                    response

            } catch (
                e: Exception
            ) {

                _error.value =
                    e.message
            }

            _isLoading.value =
                false
        }
    }
}