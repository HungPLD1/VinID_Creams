package com.example.vinid_icecreams.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.fragment.chat.FragmentChat
import com.example.vinid_icecreams.view.fragment.event.FragmentEvent
import com.example.vinid_icecreams.view.fragment.profile.FragmentProfile
import com.example.vinid_icecreams.view.fragment.store.FragmentStore
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var mBottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setFlagFullScreen()
        initView()
        addFragment()
        setDefaultFragmentBottom(savedInstanceState)
    }


    private fun addFragment() {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val fragmentStore = FragmentStore()
        //add fragmnet

        mFragmentTransaction.add(R.id.containerHome, fragmentStore)
        mFragmentTransaction.commit()

    }

    private fun setDefaultFragmentBottom(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            mBottomNavigationView?.selectedItemId = R.id.home
        }
    }

    private fun initView() {
        mBottomNavigationView = findViewById(R.id.navHomeScreen)
        mBottomNavigationView?.setOnNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.home -> {
                val mFragmentStore = FragmentStore()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerHome, mFragmentStore).addToBackStack("store").commit()
                return true
            }
            R.id.event -> {
                val mFragmentEvent = FragmentEvent()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerHome, mFragmentEvent).addToBackStack("event").commit()
                return true
            }
            R.id.chat -> {
                val mFragmentChat = FragmentChat()
                supportFragmentManager.beginTransaction().replace(R.id.containerHome, mFragmentChat)
                    .addToBackStack(null).commit()
                return true
            }
            R.id.profile -> {
                val mFragmentProfile = FragmentProfile()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.containerHome, mFragmentProfile).addToBackStack(null).commit()
                return true
            }
        }
        return false
    }


    private fun setFlagFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

}