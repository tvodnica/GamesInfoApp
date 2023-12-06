package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

data class MetacriticPlatformsApi(

    @SerializedName("metascore") var metascore: Int? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("platform") var platformApi: PlatformApi? = PlatformApi()

)