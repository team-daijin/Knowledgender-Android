package dgsw.stac.knowledgender.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
//    @POST()
//    fun register(
//        @Body register: Register
//    ): Call<Boolean>


    @POST("/api/auth/register")
    suspend fun register(
        @Body register: RegisterRequest
    ): Boolean


    @POST("/api/auth/login")
    suspend fun login(
        @Body login: LoginRequest
    ): Boolean

    @POST()
    suspend fun fetchUsername(): String
}