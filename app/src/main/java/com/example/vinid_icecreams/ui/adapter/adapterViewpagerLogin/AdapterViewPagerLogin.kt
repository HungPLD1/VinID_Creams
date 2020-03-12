package com.example.vinid_icecreams.ui.adapter.adapterViewpagerLogin

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.vinid_icecreams.ui.fragment.login.FragmentLogin
import com.example.vinid_icecreams.ui.fragment.login.FragmentRegister

class AdapterViewPagerLogin(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        when(position){
            0 -> return "Login"
            1 -> return "Register"
        }
        return super.getPageTitle(position)
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return FragmentLogin()
            1 -> return FragmentRegister()
        }
        return Fragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }
}