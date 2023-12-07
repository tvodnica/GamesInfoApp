package com.tvodnica.gamesinfo.api.apimodels

import com.google.gson.annotations.SerializedName

data class AddedByStatusApi(

    @SerializedName("yet") var yet: Int? = null,
    @SerializedName("owned") var owned: Int? = null,
    @SerializedName("beaten") var beaten: Int? = null,
    @SerializedName("toplay") var toplay: Int? = null,
    @SerializedName("dropped") var dropped: Int? = null,
    @SerializedName("playing") var playing: Int? = null

)