package com.attractgroup.bluemoon.data.model

import com.google.gson.annotations.SerializedName

class AuthToken(@SerializedName(value = "access_token", alternate = arrayOf("accessToken"))
                var accessToken: String = "",
                @SerializedName("expires_in")
                val expires: Long = 0,
                @SerializedName("refresh_token")
                var refreshToken: String? = null)