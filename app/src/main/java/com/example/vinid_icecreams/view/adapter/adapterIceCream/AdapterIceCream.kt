package com.example.vinid_icecreams.view.adapter.adapterIceCream

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
import com.squareup.picasso.Picasso

class AdapterIceCream(
    var mContext: Context?,
    var mListIceCream: ArrayList<IceCream>,
    var clicklistener: OnItemIceCreamClicklistener
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
        Picasso.with(mContext).load(mListIceCream[position].imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgIceCream)
        holder.txtNameIceCream?.text = mListIceCream[position].name
        holder.txtPriceIceCream?.text = mListIceCream[position].price.toString() + " $"
        holder.rbIceCream?.rating = mListIceCream[position].rating

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
            txtNameIceCream = itemView.findViewById(R.id.txtNameIceCream)
            txtPriceIceCream = itemView.findViewById(R.id.txtPriceIceCream)
            rawIceCream = itemView.findViewById(R.id.rawIceCream)
            imgIceCream = itemView.findViewById(R.id.imgIceCream)
            rbIceCream = itemView.findViewById(R.id.rbIceCream)
        }
    }
}