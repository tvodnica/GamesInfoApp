package com.tvodnica.gamesinfo.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RawgApi {

    @GET("genres?key=f56487c21a394900a7b37fc8d31a4958")
    fun getGenres() : Call<ApiGenreResult>

    @GET("genre/{id}")
    fun getGenreById(@Path("id") postId: Int) : Call<ApiGenre>
}