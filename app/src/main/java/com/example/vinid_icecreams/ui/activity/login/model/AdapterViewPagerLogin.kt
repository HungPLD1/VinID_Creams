package com.example.vinid_icecreams.ui.activity.login.model

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.vinid_icecreams.ui.fragment.login.login.LoginFragment
import com.example.vinid_icecreams.ui.fragment.login.register.RegisterFragment

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
            0 -> return LoginFragment()
            1 -> return RegisterFragment()
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