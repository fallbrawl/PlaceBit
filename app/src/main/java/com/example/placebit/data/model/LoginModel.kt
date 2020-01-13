package com.attractgroup.bluemoon.data.model

import com.google.gson.annotations.SerializedName

data class LoginModel(
    @SerializedName("password")
    val password: String = "",
    @SerializedName("scopes")
    val scopes:Array<String> = arrayOf("pimsy"),
    @SerializedName("firebase_token")
    val firebaseToken: String? = null,
    @SerializedName("email")
    val email: String = ""
)