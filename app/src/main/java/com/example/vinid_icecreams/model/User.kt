package com.example.vinid_icecreams.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Int,
    @SerializedName("full_name")
    var fullName: String?,
    @SerializedName("phone_number")
    var phoneNumber: Int,
    @SerializedName("password")
    var password: String,
    @SerializedName("address")
    var address: String?,
    @SerializedName("vinid_point")
    var vinIdPoint: Int?,
    @SerializedName("created_at")
    var createdAt: String?
)