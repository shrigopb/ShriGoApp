package `in`.shrigo.app.repository

import `in`.shrigo.app.models.ProfileResponse
import `in`.shrigo.app.api.RetrofitClient

class ProfileRepository {

    suspend fun getProfile(

        userId: Int,
        role: String

    ): ProfileResponse? {

        return try {

            val response =

                RetrofitClient
                    .api
                    .getProfile(

                        userId,
                        role
                    )

            if (
                response
                    .isSuccessful
            ) {

                response.body()

            } else {

                null
            }

        } catch (
            e: Exception
        ) {

            null
        }
    }
}