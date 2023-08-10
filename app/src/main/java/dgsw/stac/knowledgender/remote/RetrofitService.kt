package dgsw.stac.knowledgender.remote

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
        @Path("id") cardNewsId: Int
    ): CardNewsDetailResponse
}