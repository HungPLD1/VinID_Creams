package com.example.vinid_icecreams.ui.fragment.home.cart.model

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.squareup.picasso.Picasso
import kotlin.collections.ArrayList


class CartAdapter(
    private var context: Context?,
    private var listOrder: ArrayList<Order>,
    private var callBack: OnItemOrderListener
) : RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(context).inflate(R.layout.raw_cart, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int = listOrder.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val count = listOrder[position].amount
        holder.txtNameOrder?.text = listOrder[position].iceCream.name
        holder.txtPriceOrder?.text = listOrder[position].iceCream.price.toString()+ " $"
        listOrder[position].total = listOrder[position].iceCream.price!! * count
        holder.txtTotal?.text = listOrder[position].total.toString() + " $"
        holder.txtCount?.text = listOrder[position].amount.toString()
        callBack.insertTotal()
        Picasso.with(context).load(listOrder[position].iceCream.image_paths?.get(0))
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)

        /*handle click in plus and minus*/
        holder.btnPlus?.setOnClickListener {
          callBack.onPlus(position)
        }

        holder.btnMinus?.setOnClickListener {
            callBack.onMinus(position)
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
            txtNameOrder = itemView.findViewById(R.id.txt_name_order)
            txtPriceOrder = itemView.findViewById(R.id.txt_price_order)
            btnPlus = itemView.findViewById(R.id.btn_plus)
            btnMinus = itemView.findViewById(R.id.btn_minus)
            txtCount = itemView.findViewById(R.id.txt_count)
            txtTotal = itemView.findViewById(R.id.txt_total)
            imgOrder = itemView.findViewById(R.id.img_order)
        }
    }

    interface OnItemOrderListener {
        fun onPlus(position: Int)
        fun onMinus(position: Int)
        fun insertTotal()
    }
}