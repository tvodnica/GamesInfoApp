package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

class GameResultApi(
    @SerializedName("count") var count: Int,
    @SerializedName("results") var results: List<GameApi>
) {

}
