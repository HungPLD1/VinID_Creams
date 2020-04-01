package com.example.vinid_icecreams.model

import java.io.Serializable

data class Order (
    var iceCream : IceCream,
    var amount  : Int,
    var total :Int
): Serializable