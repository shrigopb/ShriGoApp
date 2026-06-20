package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.BookingResponse

class BookingsRepository {

    suspend fun
            getMyBookings(

        uniqueId:
        String

    ):
            List<
                    BookingResponse
                    > {

        return try {

            val response =

                RetrofitClient
                    .api
                    .getMyBookings(
                        uniqueId
                    )

            Log.d(
                "MY_BOOKINGS",
                "Code = ${
                    response.code()
                }"
            )

            Log.d(
                "MY_BOOKINGS",
                "Body = ${
                    response.body()
                }"
            )

            if (
                response
                    .isSuccessful
            ) {

                response.body()

                    ?:
                    emptyList()

            } else {

                emptyList()
            }

        } catch (
            e: Exception
        ) {

            Log.e(
                "MY_BOOKINGS",
                e.message
                    ?: ""
            )

            emptyList()
        }
    }

    //---------------------------------------
    //Get Driver Bookings
    //--------------------------------------
    suspend fun
            getDriverBookings(

        uniqueId:
        String

    ):
            List<
                    BookingResponse
                    > {

        return try {

            val response =

                RetrofitClient
                    .api
                    .getDriverBookings(
                        uniqueId
                    )

            Log.d(
                "DRIVER_BOOKINGS",
                "Code = ${
                    response.code()
                }"
            )

            Log.d(
                "DRIVER_BOOKINGS",
                "Body = ${
                    response.body()
                }"
            )

            if (
                response
                    .isSuccessful
            ) {

                response.body()

                    ?:
                    emptyList()

            } else {

                emptyList()
            }

        } catch (
            e: Exception
        ) {

            Log.e(
                "DRIVER_BOOKINGS",
                e.message
                    ?: ""
            )

            emptyList()
        }
    }
}