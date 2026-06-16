package `in`.shrigo.app.repository

import android.util.Log
import `in`.shrigo.app.api.RetrofitClient
import `in`.shrigo.app.models.BookRideRequest

class BookingRepository {
    suspend fun bookRide(
        request: BookRideRequest
    ): Boolean{
        return try {
            val response =
                RetrofitClient
                    .api
                    .bookRide(
                        request
                    )
            Log.d(
                "Book_Ride",
                "Code=${response.code()}"
            )
            Log.d(
                "BOOK_RIDE",
                "Code = ${response.code()}"
            )

            Log.d(
                "BOOK_RIDE",
                "Message = ${response.message()}"
            )

            Log.d(
                "BOOK_RIDE",
                "Error = ${
                    response.errorBody()
                        ?.string()
                }"
            )

            response.isSuccessful
        }catch (
            e: Exception
        ){
            Log.e(
                "BOOK_RIDE",
                e.message?:""
            )
            false
        }
    }

}