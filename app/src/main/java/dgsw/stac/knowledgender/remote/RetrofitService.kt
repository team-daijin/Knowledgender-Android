package dgsw.stac.knowledgender.remote

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST()
    fun register(
        @Body register: Register
    ): Call<Boolean>


    @POST()
    suspend fun register2(
        @Body register: Register
    ): Boolean

    @POST()
    suspend fun fetchUsername(): String
}