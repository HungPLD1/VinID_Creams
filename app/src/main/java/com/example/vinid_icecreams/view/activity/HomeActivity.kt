package com.example.vinid_icecreams.view.activity

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.fragment.chat.FragmentChat
import com.example.vinid_icecreams.view.fragment.event.FragmentEvent
import com.example.vinid_icecreams.view.fragment.profile.FragmentProfile
import com.example.vinid_icecreams.view.fragment.store.FragmentStore
import com.google.android.material.bottomnavigation.BottomNavigationView




class HomeActivity : AppCompatActivity(),BottomNavigationView.OnNavigationItemSelectedListener{

    var mBottomNavigationView : BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ProgressLoading.dismiss()
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
        if (savedInstanceState == null){
            mBottomNavigationView?.setSelectedItemId(R.id.home)
        }
    }

    private fun initView() {
        mBottomNavigationView = findViewById(R.id.navHomeScreen)
        mBottomNavigationView?.setOnNavigationItemSelectedListener(this)
    }



    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId) {
            R.id.home -> {
                Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                val mFragmentStore = FragmentStore()
                supportFragmentManager.beginTransaction().replace(R.id.containerHome,mFragmentStore).addToBackStack(null).commit()
            }
            R.id.event -> {
                Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                val mFragmentEvent = FragmentEvent()
                supportFragmentManager.beginTransaction().replace(R.id.containerHome,mFragmentEvent).addToBackStack(null).commit()
            }
            R.id.chat -> {
                Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                val mFragmentChat = FragmentChat()
                supportFragmentManager.beginTransaction().replace(R.id.containerHome,mFragmentChat).addToBackStack(null).commit()
            }
            R.id.profile -> {
                Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                val mFragmentProfile = FragmentProfile()
                supportFragmentManager.beginTransaction().replace(R.id.containerHome,mFragmentProfile).addToBackStack(null).commit()
            }
        }
        return false
    }
}