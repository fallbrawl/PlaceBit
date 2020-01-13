package com.attractgroup.bluemoon.data.model

import com.google.gson.annotations.SerializedName

data class Meta(
    @SerializedName("current_page")
    var currentPage: Int,
    @SerializedName("from")
    var from: Int,
    @SerializedName("last_page")
    var lastPage: Int,
    @SerializedName("path")
    var path: String,
    @SerializedName("per_page")
    var perPage: Int,
    @SerializedName("to")
    var to: Int,
    @SerializedName("total")
    var total: Int
)