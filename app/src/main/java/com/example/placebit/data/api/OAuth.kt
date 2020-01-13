package com.example.placebit.data.api

import com.example.placebit.util.Constants
import com.google.gson.annotations.SerializedName

class OAuth(@SerializedName("grant_type") @Constants.Companion.GrantType var grantType: String) {

    @SerializedName("client_id")
    private var clientId = 1
    @SerializedName("client_secret")
    private var clientSecret = "vhYutL1fowqNRiVvKhPnbgoDV5KEXr5dWHRzwXjM"
    @SerializedName("refresh_token")
    private var refreshToken: String? = null
    private val scope = "*"

    fun setRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }
}

