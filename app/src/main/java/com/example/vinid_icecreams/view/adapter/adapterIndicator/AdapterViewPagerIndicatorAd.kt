package com.example.vinid_icecreams.view.adapter.adapterIndicator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.vinid_icecreams.R
import java.util.ArrayList




class AdapterViewPagerIndicatorAd (var mContext: Context,var arrAd: ArrayList<Int>) :PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_pager_ad,container,false)

        val imageView:ImageView = mView.findViewById(R.id.imgAd)
        imageView.setImageResource(arrAd[position])
        container.addView(mView)
        return mView
    }

    override fun getCount(): Int {
        return arrAd.size
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }


}