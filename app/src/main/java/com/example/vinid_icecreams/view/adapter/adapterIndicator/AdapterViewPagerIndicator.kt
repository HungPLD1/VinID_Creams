package com.example.vinid_icecreams.view.adapter.adapterIndicator

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import java.util.*


class AdapterViewPagerIndicator(fm: FragmentManager?, arrFragment: ArrayList<Fragment>?) : FragmentPagerAdapter(fm!!) {
    private val mListFragment: List<Fragment>?

    override fun getCount(): Int {
        return mListFragment?.size ?: 0
    }

    override fun destroyItem(container: View, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as View)
    }

    override fun getItem(i: Int): Fragment {
        return mListFragment!![i]
    }

    init {
        mListFragment = arrFragment
    }
}
