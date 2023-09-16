package dgsw.stac.knowledgender.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {
//    @POST()
//    fun register(
//        @Body register: Register
//    ): Call<Boolean>

    @POST("api/auth/user/register")
    suspend fun register(@Body register: RegisterRequest)

    @POST("api/auth/login")
    suspend fun login(@Body login: LoginRequest): LoginResponse

    @GET("api/card/{id}")
    suspend fun getCardNewsDetail(
        @Path("id") cardNewsId: String
    ): CardResponse

    @GET("/api/card/")
    suspend fun cardCategory(@Query("category") category: String): CardResponseList

    @POST("/api/room/")
    suspend fun createRoom(@Header("authorization") token: String)

    @GET("/api/banner/")
    suspend fun banner(): BannerResponseList

    @GET("/api/user/")
    suspend fun getUserInfo(@Header("Authorization") token: String): Profile

    @DELETE("/api/user/")
    suspend fun deleteUserInfo(@Header("Authorization") token: String)


    @GET("/api/clinic/")
    suspend fun appointmentView(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): List<AppointmentResponse>

    @POST("/api/appointment/")
    suspend fun getReservation(
        @Header("Authorization") token: String,
        @Body data: AppointmentReservationRequest
    )

    @PUT("/api/auth/refresh")
    suspend fun accessTokenRefresh(@Header("Refresh-Token") refreshToken: String): String
}