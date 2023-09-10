package dgsw.stac.knowledgender.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
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
    ): CardNewsDetailResponse

    @GET("/api/card/")
    suspend fun cardCategory(@Query("category") category: String): List<CardCategoryResponse>

    @POST("/api/room/")
    suspend fun createRoom(@Header("authorization")token: String)

    @GET("/api/banner/")
    suspend fun banner (): List<BannerResponse>

    @GET("/api/user/")
    suspend fun getUserInfo(@Header("Authorization")token: String): Profile

    @DELETE("/api/user/")
    suspend fun deleteUserInfo(@Header("Authorization")token: String)
}