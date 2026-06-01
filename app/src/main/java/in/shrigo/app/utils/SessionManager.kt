package `in`.shrigo.app.utils

import android.content.Context

class SessionManager(

    context: Context

) {

    private val sharedPreferences =

        context.getSharedPreferences(
            "ShriGoSession",
            Context.MODE_PRIVATE
        )

    fun saveLoginSession(

        userId: Int,
        userUniqueId: String?,
        firstName: String?,
        lastName: String?,
        email: String?,
        phone: String?,
        role: String?

    ) {

        sharedPreferences.edit()
            .putBoolean(
                "isLoggedIn",
                true
            )
            .putInt(
                "userId",
                userId
            )
            .putString(
                "userUniqueId",
                userUniqueId
            )
            .putString(
                "firstName",
                firstName
            )
            .putString(
                "lastName",
                lastName
            )
            .putString(
                "email",
                email
            )
            .putString(
                "phone",
                phone
            )
            .putString(
                "role",
                role
            )
            .apply()
    }

    fun isLoggedIn(): Boolean {

        return sharedPreferences
            .getBoolean(
                "isLoggedIn",
                false
            )
    }

    fun getUserId(): Int {

        return sharedPreferences
            .getInt(
                "userId",
                0
            )
    }
    fun getUserUniqueId(): String {

        return sharedPreferences
            .getString(
                "userUniqueId",
                ""
            ) ?: ""
    }
    fun getFirstName(): String {

        return sharedPreferences
            .getString(
                "firstName",
                "Guest"
            ) ?: "Guest"
    }

    fun getLastName(): String {

        return sharedPreferences
            .getString(
                "lastName",
                ""
            ) ?: ""
    }

    fun getEmail(): String {

        return sharedPreferences
            .getString(
                "email",
                ""
            ) ?: ""
    }

    fun getPhone(): String {

        return sharedPreferences
            .getString(
                "phone",
                ""
            ) ?: ""
    }

    fun getRole(): String {

        return sharedPreferences
            .getString(
                "role",
                ""
            ) ?: ""
    }

    fun logout() {

        sharedPreferences
            .edit()
            .clear()
            .apply()
    }
}