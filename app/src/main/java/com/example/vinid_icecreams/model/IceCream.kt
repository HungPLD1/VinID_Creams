package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class IceCream (
    @SerializedName("id")
    var id: Int?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("type")
    var type: String?,
    @SerializedName("image_paths")
    var image_paths: ArrayList<String>?,
    @SerializedName("price")
    var price: Int?,
    var rating: Float?,
    @SerializedName("created_at")
    var createdAt: String?,
    @SerializedName("ratings")
    var listComment:ArrayList<Comment>?
):Serializable