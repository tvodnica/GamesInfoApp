package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

class GenreApi(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("slug") var slug: String,
    @SerializedName("games_count") var gamesCount: Int,
    @SerializedName("image_background") var image: String
) {

}
