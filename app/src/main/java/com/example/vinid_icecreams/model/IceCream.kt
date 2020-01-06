package com.example.vinid_icecreams.model

import java.io.Serializable


data class IceCream (
    /*
    type of ice cream
     0: Chocolate
     1: Matcha
     2: Strawberry
     3: Cacao
     4: Vani
     5: Other
     6: Mix
     */
    var id: Int,
    var name: String,
    var type: String,
    var image_paths: ArrayList<String>,
    var price: Int,
    var discount: Int,
    var rating: Float,
    var createdAt: Long,
    var listComment:ArrayList<Comment>
):Serializable