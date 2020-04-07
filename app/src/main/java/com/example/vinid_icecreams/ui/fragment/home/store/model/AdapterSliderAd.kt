package com.example.vinid_icecreams.ui.fragment.home.store.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.vinid_icecreams.R
import com.smarteist.autoimageslider.SliderViewAdapter
import java.util.*


class AdapterSliderAd (var mContext: Context, var arrAd: ArrayList<Int>) :
    SliderViewAdapter<AdapterSliderAd.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup?): SliderAdapterVH {
        val view = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_pager_ad,parent,false)
        return SliderAdapterVH(
            view
        )
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH?, position: Int) {
        viewHolder?.mImageView?.setImageResource(arrAd[position])
    }

    override fun getCount(): Int {
        return arrAd.size
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var mImageView :ImageView = itemView.findViewById(R.id.imgAd)

    }
}