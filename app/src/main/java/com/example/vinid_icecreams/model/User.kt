package com.example.vinid_icecreams.model

import java.sql.Timestamp

data class User(
    var id: Int,
    var fullName: String,
    var useName: String,
    var email: String,
    var phoneNumber: String,
    var password: String,
    var address: String,
    var vinIdPoint: Int,
    var status: Int,
    var role: Int,
    var createdAt: Timestamp
)