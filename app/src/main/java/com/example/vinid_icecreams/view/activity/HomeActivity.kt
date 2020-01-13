package com.example.vinid_icecreams.view.activity

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.plusAssign
import androidx.navigation.ui.setupWithNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.navigation.KeepStateNavigator
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {

    private var mBottomNavigationView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setFlagFullScreen()
        initView()
        setupNavigation()
    }

    private fun setupNavigation() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)!!
        //set up custom navigator
        val navigator = KeepStateNavigator(this,navHostFragment.childFragmentManager,R.id.nav_host_fragment)
        navController.navigatorProvider += navigator
        // set navigation graph
        navController.setGraph(R.navigation.nav_graph)
        mBottomNavigationView?.setupWithNavController(navController)
    }


    private fun initView() {
        mBottomNavigationView = findViewById(R.id.navHomeScreen)
    }

    private fun setFlagFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

}