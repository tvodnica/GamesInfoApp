package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName


data class StoresApi (

  @SerializedName("id"    ) var id    : Int?    = null,
  @SerializedName("url"   ) var url   : String? = null,
  @SerializedName("store" ) var storeApi : StoreApi?  = StoreApi()

)