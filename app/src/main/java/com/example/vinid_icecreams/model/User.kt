package com.example.vinid_icecreams.model

data class User(
    var id: Int,
    var fullName: String?,
    var phoneNumber: Int,
    var password: String,
    var address: String?,
    var vinIdPoint: Int?,
    var createdAt: Long?
)