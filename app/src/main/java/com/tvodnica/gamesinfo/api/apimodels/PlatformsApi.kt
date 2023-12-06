package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName


data class PlatformsApi(

    @SerializedName("platform") var platformApi: PlatformApi? = PlatformApi(),
    @SerializedName("released_at") var releasedAt: String? = null,
    @SerializedName("requirements") var requirementsApi: RequirementsApi? = RequirementsApi()

)