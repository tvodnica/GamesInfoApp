package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

class GenreResultApi(
    @SerializedName("count") var count: Int,
    @SerializedName("results") var results: List<GenreApi>
) {

}
