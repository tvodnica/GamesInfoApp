package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName


data class ParentPlatformsApi (

  @SerializedName("platform" ) var platformApi : PlatformApi? = PlatformApi()

)