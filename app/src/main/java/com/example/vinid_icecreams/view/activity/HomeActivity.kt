package com.example.vinid_icecreams.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.fragment.store.FragmentStore

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ProgressLoading.dismiss()
        initView()
        setupFragment()
    }

    private fun initView() {

    }

    private fun setupFragment() {
        var mFragmentStore = FragmentStore()
        supportFragmentManager.beginTransaction().add(R.id.containerHome,mFragmentStore).commit()
    }
}