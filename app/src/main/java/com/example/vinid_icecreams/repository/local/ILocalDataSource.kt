package com.example.vinid_icecreams.repository.local

interface ILocalDataSource {
    fun saveToken(token : String?)
    fun getToken() : String?
}