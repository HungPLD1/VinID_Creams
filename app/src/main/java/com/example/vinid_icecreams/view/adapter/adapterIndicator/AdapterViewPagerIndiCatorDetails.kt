package com.example.vinid_icecreams.view.adapter.adapterIndicator

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.vinid_icecreams.R
import com.squareup.picasso.Picasso
import java.util.ArrayList

class AdapterViewPagerIndiCatorDetails (var mContext: Context, var arrDetails: ArrayList<String>) :
    PagerAdapter() {

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as View
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_pager_details,container,false)

        val imageView: ImageView = mView.findViewById(R.id.imgDetails)
        Picasso.with(mContext).load(arrDetails[position])
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(imageView)

        container.addView(mView)
        return mView
    }

    override fun getCount(): Int {
        return arrDetails.size
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object`as View)
    }


}