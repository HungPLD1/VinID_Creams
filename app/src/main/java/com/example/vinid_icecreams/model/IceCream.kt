package com.example.vinid_icecreams.model

import java.io.Serializable


data class IceCream (

    var id: Int,
    var name: String,
    var type: String,
    var image_paths: ArrayList<String>,
    var price: Int,
    var rating: Float,
    var createdAt: Long,
    var listComment:ArrayList<Comment>
):Serializable