package com.example.placebit.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QrCodeModel(
    @SerializedName("id")
    var id: String = "",
    @SerializedName("id")
    var qrCode: String = ""
) : Serializable