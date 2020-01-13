package com.example.placebit.data.api

import com.attractgroup.bluemoon.data.model.AuthToken
import com.attractgroup.bluemoon.data.model.ErrorModel
import com.attractgroup.bluemoon.data.model.Meta
import com.attractgroup.bluemoon.data.model.User
import com.google.gson.annotations.SerializedName


data class AppResponse<T>(var error: String?, var data: T,
                          var user: User?, var token: AuthToken?,
                          var errors: ArrayList<ErrorModel>?,
                          @SerializedName(value = "access_token", alternate = arrayOf("accessToken"))
                          var accessToken: String,
                          @SerializedName(value = "refresh_token", alternate = arrayOf("refreshToken"))
                          var refreshToken: String,
                          var meta: Meta? = null)

//country.isoCode.toString(),