package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

data class RatingsApi(

    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("count") var count: Int? = null,
    @SerializedName("percent") var percent: Double? = null

)