package com.example.vinid_icecreams.repository.local

import android.content.SharedPreferences
import com.example.vinid_icecreams.utils.Const
import io.realm.Realm
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val realm: Realm,
    private val sharedPref :SharedPreferences
) : ILocalDataSource {

    override fun saveToken(token: String?) {
        sharedPref.edit().putString(Const.TOKEN,token).apply()
    }

    override fun getToken(): String? {
        return sharedPref.getString(Const.TOKEN,null)
    }
}