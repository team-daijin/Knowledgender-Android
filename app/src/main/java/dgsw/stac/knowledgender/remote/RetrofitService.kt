package dgsw.stac.knowledgender.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import site.algosipeosseong.model.Banner
import site.algosipeosseong.model.Cardnews
import site.algosipeosseong.model.CardnewsCategory
import site.algosipeosseong.model.CardnewsDetail
import site.algosipeosseong.model.Category
import site.algosipeosseong.model.ClinicRequest

interface RetrofitService {
//    @POST()
//    fun register(
//        @Body register: Register
//    ): Call<Boolean>

    @POST("api/auth/user/register")
    suspend fun register(@Body register: RegisterRequest)

    @POST("api/auth/login")
    suspend fun login(@Body login: LoginRequest): LoginResponse

    @GET("card/{id}")
    suspend fun getCardNewsDetail(
        @Path("id") cardNewsId: String
    ): CardnewsDetail

    @GET("card")
    suspend fun getCardnews(): List<Cardnews>

    @GET("card/category/{category}")
    suspend fun getCardnewsByCategory(@Path("category")category: String): List<CardnewsCategory>

    @POST("room")
    suspend fun createRoom(@Header("authorization") token: String)

    @GET("banner")
    suspend fun banner(): List<Banner>

    @GET("user")
    suspend fun getUserInfo(@Header("Authorization") token: String): Profile

    @DELETE("user")
    suspend fun deleteUserInfo(@Header("Authorization") token: String)


    @GET("clinic")
    suspend fun appointmentView(
        @Query("radius") radius: Int = 5000,
        @Body location: ClinicRequest
    ): List<AppointmentResponse>

    @POST("/api/appointment/")
    suspend fun getReservation(
        @Header("Authorization") token: String,
        @Body data: AppointmentReservationRequest
    )

    @PUT("/api/auth/refresh")
    suspend fun accessTokenRefresh(@Header("Refresh-Token") refreshToken: String): String
}