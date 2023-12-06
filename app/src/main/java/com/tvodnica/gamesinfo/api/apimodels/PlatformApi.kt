package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName


data class PlatformApi(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("year_end") var yearEnd: String? = null,
    @SerializedName("year_start") var yearStart: Int? = null,
    @SerializedName("games_count") var gamesCount: Int? = null,
    @SerializedName("image_background") var imageBackground: String? = null

)