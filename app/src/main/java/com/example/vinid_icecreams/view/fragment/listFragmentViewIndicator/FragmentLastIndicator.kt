package com.example.vinid_icecreams.view.fragment.listFragmentViewIndicator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.view.adapter.adapterViewpagerLogin.AdapterViewPagerLogin
import kotlinx.android.synthetic.main.fragment_indicator_3.*

class FragmentLastIndicator : Fragment(), View.OnClickListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_indicator_3,container,false)
        initView()
        return view
    }

    /*func hanle setup view*/
    private fun initView() {
        setUpFormLoginAndRegister()
    }

    private fun setUpFormLoginAndRegister() {
            var mAdapterViewPagerLogin = AdapterViewPagerLogin(childFragmentManager)
            viewPagerLogin.adapter = mAdapterViewPagerLogin
            mAdapterViewPagerLogin.notifyDataSetChanged()
            viewTabLayout.setupWithViewPager(viewPagerLogin)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}