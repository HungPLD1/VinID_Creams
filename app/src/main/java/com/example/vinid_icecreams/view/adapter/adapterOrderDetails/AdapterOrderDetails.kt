package com.example.vinid_icecreams.view.adapter.adapterOrderDetails

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.ItemOrder
import com.squareup.picasso.Picasso

class AdapterOrderDetails(
    private var mContext: Context?,
    private var mListItemOrder: ArrayList<ItemOrder>
) : RecyclerView.Adapter<AdapterOrderDetails.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView =
            LayoutInflater.from(mContext).inflate(R.layout.raw_layout_order_details, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListItemOrder.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNameOrder?.text = mListItemOrder[position].iceCreamInfo.name
        holder.txtPriceOrder?.text = mListItemOrder[position].iceCreamInfo.price.toString() + " $"
        holder.txtCountOrder?.text = mListItemOrder[position].quantity.toString()
        holder.txtTotalOrder?.text =
            (mListItemOrder[position].iceCreamInfo.price * mListItemOrder[position].quantity).toString() + " $"

        Picasso.with(mContext).load(mListItemOrder[position].iceCreamInfo.imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNameOrder: TextView? = null
        var txtCountOrder: TextView? = null
        var imgOrder: ImageView? = null
        var txtTotalOrder: TextView? = null
        var txtPriceOrder: TextView? = null

        init {
            txtNameOrder = itemView.findViewById(R.id.txt_history_details_name)
            txtCountOrder = itemView.findViewById(R.id.txt_history_details_count)
            imgOrder = itemView.findViewById(R.id.txt_history_details_name)
            txtTotalOrder = itemView.findViewById(R.id.txt_history_details_total)
            txtPriceOrder = itemView.findViewById(R.id.txt_history_details_price)
        }
    }
}



