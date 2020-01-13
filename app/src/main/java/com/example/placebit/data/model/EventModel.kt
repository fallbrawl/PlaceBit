package com.example.placebit.data.model

import com.google.gson.annotations.SerializedName

data class EventModel(
    @SerializedName("id")
    var id: String = "",
    var isFavorited:Boolean = false,
    @SerializedName("type")
    var type:String = "concert",
    @SerializedName("image")
    var image:String = "",
    @SerializedName("isOnSale")
    var isOnSale:Boolean = true,
    @SerializedName("price")
    var price:Float = 100f,
    @SerializedName("name")
    var name:String = "Global Gathering",
    @SerializedName("description")
    var description:String = "Biggest electronic music fest",
    @SerializedName("address")
    var address:String = "г. Киев, стадион так стадион",
    @SerializedName("date")
    var date:String = "13 Июля 2020 г., 19:30",
    var position: Int = -1
)