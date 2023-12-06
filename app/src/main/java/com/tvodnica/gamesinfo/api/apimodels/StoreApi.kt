package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

data class StoreApi(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("slug") var slug: String? = null,
    @SerializedName("domain") var domain: String? = null,
    @SerializedName("games_count") var gamesCount: Int? = null,
    @SerializedName("image_background") var imageBackground: String? = null

)