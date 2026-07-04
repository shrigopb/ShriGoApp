package `in`.shrigo.app.api

import `in`.shrigo.app.models.BookRideRequest
import `in`.shrigo.app.models.BookingResponse
import `in`.shrigo.app.models.ForgotPasswordRequest
import `in`.shrigo.app.models.ForgotPasswordResponse
import `in`.shrigo.app.models.Ride
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import `in`.shrigo.app.models.LoginRequest
import `in`.shrigo.app.models.LoginResponse
import `in`.shrigo.app.models.ProfileResponse
import retrofit2.http.Path
import `in`.shrigo.app.models.UploadRideRequest
import `in`.shrigo.app.models.UploadRideResponse
import `in`.shrigo.app.models.MyRideResponse
import `in`.shrigo.app.models.NotificationCountResponse
import `in`.shrigo.app.models.NotificationResponse
import `in`.shrigo.app.models.SignupRequest
import `in`.shrigo.app.models.SignupResponse
import `in`.shrigo.app.models.UpdateRideResponse
import `in`.shrigo.app.models.VersionResponse
import retrofit2.http.DELETE
import retrofit2.http.PUT

interface ApiService {
    //--------------------------------------
    //Get Methods
    //--------------------------------------
    //Ride Info API
    @GET("api/RideApi/active")
    suspend fun getRides(): List<Ride>

    //Profile Info API
    @GET(
        "api/ProfileApi/{userId}/{role}"
    )

    suspend fun getProfile(

        @Path("userId")
        userId: Int,

        @Path("role")
        role: String

    ): Response<ProfileResponse>


    //--------------------------------------
    //Post Methods
    //--------------------------------------

    //Login Info API
    @POST("api/LoginApi")
    suspend fun loginUser(

        @Body request: LoginRequest

    ): Response<LoginResponse>


//--------------------------------------
// Upload Ride API
//--------------------------------------

    @POST("api/RideApi/upload")
    suspend fun uploadRide(

        @Body request:
        UploadRideRequest

    ): Response<UploadRideResponse>

    //---------------------------------
    //Get My Rides
    //----------------------------------
    @GET("api/RideApi/myrides/{uniqueId}")
    suspend fun getMyRides(

        @Path("uniqueId")
        uniqueId: String

    ): Response<List<MyRideResponse>>

    //------------------------------
    // Delete selected ride
    //---------------------------------
    @DELETE(
        "api/RideApi/delete/{rideId}"
    )
    suspend fun deleteRide(

        @Path(
            "rideId"
        )
        rideId: Int

    ): Response<Unit>

    //---------------------------------
    // Update Ride
    //---------------------------------
    @PUT(
        "api/RideApi/updateride/{rideId}"
    )
    suspend fun updateRide(

        @Path("rideId")
        rideId: Int,

        @Body
        request: UploadRideRequest

    ): Response<UpdateRideResponse>

    //--------------------------------------
    // Signup API
    //--------------------------------------
    @POST(
        "api/SignupApi"
    )
    suspend fun signupUser(

        @Body
        request:
        SignupRequest

    ): Response<SignupResponse>


    //-----------------------------------
    //ForgotPassword Api
    //-----------------------------------

    @POST("api/ForgotPasswordApi")
    suspend fun forgotPassword(
        @Body request: ForgotPasswordRequest
    ): Response<ForgotPasswordResponse>

    //--------------------
    //Book Ride
    //----------------------
    @POST(
        "api/BookingApi/bookride"
    )
    suspend fun bookRide(
        @Body
        request:
        BookRideRequest
    ): Response<BookingResponse>

    //---------------------------------
    // Get My Bookings
    //---------------------------------
    @GET(
        "api/BookingApi/mybookings/{uniqueId}"
    )
    suspend fun getMyBookings(

        @Path(
            "uniqueId"
        )
        uniqueId: String

    ): Response<
            List<BookingResponse>
            >

    //---------------------------------
    // Get Driver Bookings
    //---------------------------------
    @GET(
        "api/BookingApi/driverbookings/{uniqueId}"
    )
    suspend fun getDriverBookings(

        @Path(
            "uniqueId"
        )
        uniqueId: String

    ): Response<
            List<BookingResponse>
            >

    //-------------------------------
    //Notification
    //-------------------------------
    @GET(
        "api/NotificationApi/{uniqueId}"
    )
    suspend fun getNotifications(

        @Path("uniqueId")
        uniqueId: String

    ): List<NotificationResponse>

    //-------------------------------
    // Notification Count
    //--------------------------------
    @GET(
        "api/NotificationApi/count/{uniqueId}"
    )
    suspend fun getNotificationCount(

        @Path("uniqueId")
        uniqueId: String

    ):  Response<NotificationCountResponse>

    //--------------------------------
    // Mark All As Read
    //--------------------------------
    @POST(
        "api/NotificationApi/markallread/{uniqueId}"
    )
    suspend fun markAllAsRead(

        @Path("uniqueId")
        uniqueId: String

    ): Response<Unit>

    //--------------------------------
    // Latest Version Update
    //--------------------------------
    @GET("api/VersionApi/Latest")
    suspend fun getLatestVersion(): VersionResponse
}

