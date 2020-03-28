package com.example.vinid_icecreams.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.activity.BaseActivity
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.ui.activity.home.HomeActivity
import com.example.vinid_icecreams.ui.adapter.adapterIndicator.AdapterViewPagerIndicator
import com.example.vinid_icecreams.ui.fragment.login.listFragmentViewIndicator.FragmentFirstIndicator
import com.example.vinid_icecreams.ui.fragment.login.listFragmentViewIndicator.FragmentLastIndicator
import com.example.vinid_icecreams.ui.fragment.login.listFragmentViewIndicator.FragmentSecondIndicator
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : BaseActivity<LoginMainViewModel>() {
    private var mAdapterViewPagerIndicator: AdapterViewPagerIndicator? = null

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val loginMainViewModel: LoginMainViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginMainViewModel::class.java)
    }

    override fun providerViewModel(): LoginMainViewModel = loginMainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        observeData()
        setFlagFullScreen()
        setupViewIndicator()
    }

    private fun observeData() {
        loginMainViewModel.isHaveToken.observe(this, Observer {
            if (it) {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
        })
        loginMainViewModel.checkIsLogin()
    }

    private fun setupViewIndicator() {
        if (mAdapterViewPagerIndicator == null) {
            mAdapterViewPagerIndicator =
                AdapterViewPagerIndicator(supportFragmentManager, getListFragmentIndicator())
            loginViewPager.adapter = mAdapterViewPagerIndicator
            loginDotsIndicator.setViewPager(loginViewPager)
        }
    }

    private fun getListFragmentIndicator(): ArrayList<Fragment> {
        val listIndicator: ArrayList<Fragment> = ArrayList()
        listIndicator.add(FragmentFirstIndicator())
        listIndicator.add(FragmentSecondIndicator())
        listIndicator.add(FragmentLastIndicator())
        return listIndicator
    }

    /*setup SDK facebook*/
    private fun setupSDKFaceBook() {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    /*Set full screen for waiting activity ui*/
    private fun setFlagFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    companion object {
        private var LOG = LoginActivity::class.java.name
        private var REFERENCE = "com.example.vinid_icecreams"
    }
}
