package com.tvodnica.gamesinfo.api

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.tvodnica.gamesinfo.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.system.exitProcess

private const val BASE_URL = "https://api.rawg.io/api/"

class RawgClient() {

    var rawgApi: RawgApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        rawgApi = retrofit.create(RawgApi::class.java)
    }

    fun getGenres() {

    }
}
