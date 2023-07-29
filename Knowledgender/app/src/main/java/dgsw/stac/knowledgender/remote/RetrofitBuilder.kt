package dgsw.stac.knowledgender.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    var retrofitService : RetrofitService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("대충 주소")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitService = retrofit.create(RetrofitService::class.java)
    }
}