package `in`.shrigo.app.models

data class LoginRequest(

    val emailorPhone:String,
    val password:String
)