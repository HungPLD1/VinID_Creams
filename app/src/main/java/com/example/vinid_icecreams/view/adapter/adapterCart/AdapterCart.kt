package com.example.vinid_icecreams.view.adapter.adapterCart

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.squareup.picasso.Picasso

class AdapterCart(
    var mContext: Context?,
    var mListOrder: ArrayList<Order>
) : RecyclerView.Adapter<AdapterCart.MyViewHolder>() {
    var mCount: Int = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_store, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListOrder.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNameOrder?.text = mListOrder[position].mNameOrder
        holder.txtPriceOrder?.text = mListOrder[position].mPrice.toString()
        holder.txtTotal?.text = (mListOrder[position].mPrice * mCount).toString()
        holder.txtCount?.text = mCount.toString()

        holder.txtCount?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(count: Editable?) {
                mCount = count as Int
                holder.txtTotal?.text = (mCount * mListOrder[position].mPrice).toString()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        Picasso.with(mContext).load(mListOrder[position].mImageOrder)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)

        /*handle click in plus and minus*/
        holder.btnPlus?.setOnClickListener {
            if (mCount < 99) {
                mCount += 1
            }
        }

        holder.btnMinus?.setOnClickListener {
            if (mCount > 1){
                mCount -= 1
            }
        }



    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNameOrder: TextView? = null
        var txtPriceOrder: TextView? = null
        var btnPlus: ImageView? = null
        var btnMinus: ImageView? = null
        var txtCount: TextView? = null
        var txtTotal: TextView? = null
        var imgOrder: ImageView? = null

        init {
            txtNameOrder = itemView.findViewById(R.id.txt_nameCart)
            txtPriceOrder = itemView.findViewById(R.id.txtPriceCart)
            btnPlus = itemView.findViewById(R.id.btnPlus)
            btnMinus = itemView.findViewById(R.id.btnMinus)
            txtCount = itemView.findViewById(R.id.count)
            txtTotal = itemView.findViewById(R.id.txtTotalCart)
            imgOrder = itemView.findViewById(R.id.imgOrder)
        }
    }
}