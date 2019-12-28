package com.example.vinid_icecreams.repository

import com.example.vinid_icecreams.model.User

class Repository {
    val CODE_200 = 200

    companion object{
        val mInstace = Repository()
    }

    fun callRequestLogin(): User?{
        val mUser : User? = null

        return mUser
    }
}