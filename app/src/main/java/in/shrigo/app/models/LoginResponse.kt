package `in`.shrigo.app.models

data class LoginResponse(

    val success: Boolean = false,

    val firstName: String? = null,

    val phone: String? = null,

    val uniqueId: String? = null
)



