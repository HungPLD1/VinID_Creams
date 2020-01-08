package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class Store(
    @SerializedName("id")
    var id:Int,
    @SerializedName("name")
    var name:String,
    @SerializedName("address")
    var address: String,
    @SerializedName("image_path")
    var imagePath :String,
    @SerializedName("latitude")
    var latitude: Double,
    @SerializedName("longitude")
    var longitude: Double,
    @SerializedName("created_at")
    var createdAt: String,
    var range : Double
)
