package com.example.vinid_icecreams.ui.adapter.adapterIceCream

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.utils.CommonUtils
import com.squareup.picasso.Picasso

class AdapterIceCream(
    private var mContext: Context?,
    private var mListIceCream: ArrayList<IceCream>,
    private var clicklistener: OnItemIceCreamClicklistener
) : RecyclerView.Adapter<AdapterIceCream.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView =
            LayoutInflater.from(mContext).inflate(R.layout.raw_layout_ice_cream, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListIceCream.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var type = mListIceCream[position].type
        /*
        0: Chocolate
        1: Matcha
        2: Strawberry
        3: Cacao
        4: Vani
        5: Other
        6: Mix
        */
        Picasso.with(mContext).load(mListIceCream[position].image_paths?.get(0))
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgIceCream)
        holder.txtNameIceCream?.text = mListIceCream[position].name
        holder.txtPriceIceCream?.text = mListIceCream[position].price.toString() + " $"

        val mListComment = mListIceCream[position].listComment.orEmpty()
        val mListRatingBar = mutableListOf<Int>()
        for (element in mListComment) {
            mListRatingBar.add(element.rating_star)
        }
            holder.rbIceCream?.rating = CommonUtils.instace.calculateAverage(mListRatingBar)

        /*handle click on item Store*/
        holder.rawIceCream?.setOnClickListener {
            clicklistener.onItemClick(position)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNameIceCream: TextView? = null
        var txtPriceIceCream: TextView? = null
        var rawIceCream: LinearLayout? = null
        var imgIceCream: ImageView? = null
        var rbIceCream: RatingBar? = null

        init {
            txtNameIceCream = itemView.findViewById(R.id.txt_name_iceCream)
            txtPriceIceCream = itemView.findViewById(R.id.txt_price_iceCream)
            rawIceCream = itemView.findViewById(R.id.rawIceCream)
            imgIceCream = itemView.findViewById(R.id.img_iceCream)
            rbIceCream = itemView.findViewById(R.id.rb_iceCream)
        }
    }

    /*handle search item in list ice cream*/
    fun filter( input: String) {
        var text = input
        val mListCopyIceCream: ArrayList<IceCream> = ArrayList()
        mListCopyIceCream.addAll(mListIceCream)
        mListIceCream.clear()
        if (text.isEmpty()) {
            mListIceCream.addAll(mListCopyIceCream)
        } else {
            text = text.toLowerCase()
            for (iceCream in mListCopyIceCream) {
                if (iceCream.name!!.toLowerCase().contains(text)) {
                    mListIceCream.add(iceCream)
                }
            }
        }
        notifyDataSetChanged()
    }
}