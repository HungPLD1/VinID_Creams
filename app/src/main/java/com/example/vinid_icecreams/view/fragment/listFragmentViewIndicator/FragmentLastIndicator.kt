package com.example.vinid_icecreams.view.fragment.listFragmentViewIndicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.view.adapter.adapterViewpagerLogin.AdapterViewPagerLogin
import com.google.android.material.tabs.TabLayout

class FragmentLastIndicator : Fragment(), View.OnClickListener {
    var mPager : ViewPager? = null
    var mTabLayout : TabLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_indicator_3, container, false)

        initView(view)
        return view
    }

    /*func hanle setup view*/
    private fun initView(view: View) {
        mPager = view.findViewById(R.id.viewPagerLogin)
        mTabLayout = view.findViewById(R.id.viewTabLayout)
        setUpFormLoginAndRegister()
    }

    private fun setUpFormLoginAndRegister() {
        val mAdapterViewPagerLogin = AdapterViewPagerLogin(childFragmentManager)
        mPager!!.adapter = mAdapterViewPagerLogin
        mAdapterViewPagerLogin.notifyDataSetChanged()
        mTabLayout!!.setupWithViewPager(mPager)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}