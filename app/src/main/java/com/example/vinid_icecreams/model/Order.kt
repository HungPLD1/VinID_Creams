package com.example.vinid_icecreams.model

import java.io.Serializable

class Order (
    var mIdOrder: Int,
    var mImageOrder : String,
    var mNameOrder : String,
    var mAmount  : Int,
    var mPrice : Int
): Serializable