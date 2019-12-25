package com.example.vinid_icecreams.model

data class User(
    var id: Int,
    var fullName: String,
    var useName: String,
    var email: String,
    var phoneNumber: Int,
    var password: String,
    var address: String,
    var vinIdPoint: Int,
    var status: Int,
    var role: Int,
    var createdAt: Long
)