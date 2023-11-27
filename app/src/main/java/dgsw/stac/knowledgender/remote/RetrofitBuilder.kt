package dgsw.stac.knowledgender.remote

import dgsw.stac.knowledgender.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder {
    companion object {
        private fun getRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .callFactory(
                    OkHttpClient.Builder()
                        .addInterceptor(
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        ).build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        val apiService: RetrofitService = getRetrofit().create(RetrofitService::class.java)
    }
}