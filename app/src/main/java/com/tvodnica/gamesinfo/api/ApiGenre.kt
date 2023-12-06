package com.tvodnica.gamesinfo.api

import com.google.gson.annotations.SerializedName

class ApiGenre(
    @SerializedName("id") var id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("slug") var slug: String,
    @SerializedName("games_count") var gamesCount: Int,
    @SerializedName("image_background") var image: String
) {

}
