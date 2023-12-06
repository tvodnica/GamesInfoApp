package com.tvodnica.gamesinfo.api

import com.google.gson.annotations.SerializedName

class ApiGenreResult(
    @SerializedName("count") var count: Int,
    @SerializedName("results") var results: List<ApiGenre>
) {

}
