package com.example.vinid_icecreams.model

import java.io.Serializable

class Order (
    var mIceCream : IceCream,
    var mAmount  : Int,
    var mTotal :Int
): Serializable