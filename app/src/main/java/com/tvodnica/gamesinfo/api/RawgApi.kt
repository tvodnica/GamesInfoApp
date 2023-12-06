package com.tvodnica.gamesinfo.api

import com.tvodnica.gamesinfo.api.apimodels.GameApi
import com.tvodnica.gamesinfo.api.apimodels.GameResultApi
import com.tvodnica.gamesinfo.api.apimodels.GenreApi
import com.tvodnica.gamesinfo.api.apimodels.GenreResultApi
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RawgApi {
    @GET("genres")
    fun getGenres() : Call<GenreResultApi>

    @GET("games")
    fun getGamesByGenres(@Query("genres") id: String) : Call<GameResultApi>

    @GET("games/{id}")
    fun getGameById(@Path("id") id: Int) : Call<GameApi>
}