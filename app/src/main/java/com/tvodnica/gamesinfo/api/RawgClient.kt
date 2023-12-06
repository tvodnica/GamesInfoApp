package com.tvodnica.gamesinfo.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.rawg.io/api/"
private const val KEY = "f56487c21a394900a7b37fc8d31a4958"

class RawgClient {

    var rawgApi: RawgApi

    init {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(KEY))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        rawgApi = retrofit.create(RawgApi::class.java)
    }


}
class AuthInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val originalRequest = chain.request()
        val newUrl = originalRequest.url().newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val newRequest = originalRequest.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}