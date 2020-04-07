package com.example.vinid_icecreams.ui.fragment.login.listFragmentViewIndicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.ui.activity.login.model.AdapterViewPagerLogin
import kotlinx.android.synthetic.main.fragment_indicator_3.*

class FragmentLastIndicator : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_indicator_3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    /*func handle setup view*/
    private fun initView() {
        setUpFormLoginAndRegister()
    }

    private fun setUpFormLoginAndRegister() {
        val mAdapterViewPagerLogin =
            AdapterViewPagerLogin(
                childFragmentManager
            )
        viewPagerLogin!!.adapter = mAdapterViewPagerLogin
        mAdapterViewPagerLogin.notifyDataSetChanged()
        tabLayoutLogin!!.setupWithViewPager(viewPagerLogin)
    }

}