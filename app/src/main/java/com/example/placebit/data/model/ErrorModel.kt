package com.attractgroup.bluemoon.data.model

import com.google.gson.annotations.SerializedName

data class ErrorModel(@SerializedName("file")
                      val file: Any? = null,
                      @SerializedName("field")
                      val field: String = "",
                      @SerializedName("line")
                      val line: Int = 0,
                      @SerializedName("values")
                      val values: List<String>?,
                      @SerializedName("type")
                      val type: String = "",
                      @SerializedName("errors")
                      val errors: List<String>?)