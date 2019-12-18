package com.example.vinid_icecreams.view.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndicator
import com.example.vinid_icecreams.view.fragment.listFragmentViewIndicator.FragmentFirstIndicator
import com.example.vinid_icecreams.view.fragment.listFragmentViewIndicator.FragmentLastIndicator
import com.example.vinid_icecreams.view.fragment.listFragmentViewIndicator.FragmentSecondIndicator
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import kotlinx.android.synthetic.main.activity_waiting.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class WaitingActivity : AppCompatActivity() {
    private var LOG = "HungPLD1"
    private var REFERENCE = "com.example.vinid_icecreams"

    private var  mAdapterViewPagerIndicator : AdapterViewPagerIndicator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_waiting)
        setFlagFullScreen()
        getHaskey()
        //setupSDKFaceBook()
        setupViewIndicator()
    }

    private fun setupViewIndicator() {
        if (mAdapterViewPagerIndicator == null){
            mAdapterViewPagerIndicator = AdapterViewPagerIndicator(supportFragmentManager,getListFragmentIndicator())
            mViewPager.adapter = mAdapterViewPagerIndicator
            mDotsIndicator.setViewPager(mViewPager)
        }
    }

    private fun getListFragmentIndicator(): ArrayList<Fragment> {
        var mListIndicator: ArrayList<Fragment> = ArrayList()
        mListIndicator.add(FragmentFirstIndicator())
        mListIndicator.add(FragmentSecondIndicator())
        mListIndicator.add(FragmentLastIndicator())
        return mListIndicator
    }

    /*setup SDK facebook*/
    private fun setupSDKFaceBook() {
        FacebookSdk.sdkInitialize(applicationContext)
        AppEventsLogger.activateApp(this)
    }

    /*get has key*/
    @SuppressLint("PackageManagerGetSignatures")
    private fun getHaskey() {
        try {
            val info = packageManager.getPackageInfo(
                REFERENCE,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d(
                    LOG,
                    Base64.encodeToString(md.digest(), Base64.DEFAULT)
                )
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }

    /*Set full screen for waiting activity ui*/
    private fun setFlagFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}
