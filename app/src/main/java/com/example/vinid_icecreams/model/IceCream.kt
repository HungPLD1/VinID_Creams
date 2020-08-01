package com.example.vinid_icecreams.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class IceCream (
    @SerializedName("id")
    @PrimaryKey
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

data class Comment(
    @SerializedName("user_name")
    var user_name: String,
    @SerializedName("user_avatar")
    var user_avatar: String,
    @SerializedName("rating_star")
    var rating_star : Int,
    @SerializedName("comment")
    var comment :String
)