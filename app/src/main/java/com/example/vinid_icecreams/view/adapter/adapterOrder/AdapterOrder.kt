package com.example.vinid_icecreams.view.adapter.adapterOrder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.squareup.picasso.Picasso
import java.util.*
import java.util.logging.Handler
import kotlin.collections.ArrayList
import kotlin.concurrent.timerTask


class AdapterOrder(
    private var mContext: Context?,
    private var mListOrder: ArrayList<Order>,
    private var mCallBack: OnItemOrderListener
) : RecyclerView.Adapter<AdapterOrder.MyViewHolder>() {
    var TAG = "hungpld1order"


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_cart, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListOrder.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var mCount = mListOrder[position].mAmount

        holder.txtNameOrder?.text = mListOrder[position].mIceCream.name
        holder.txtPriceOrder?.text = mListOrder[position].mIceCream.price.toString()
        mListOrder[position].mTotal = mListOrder[position].mIceCream.price * mCount
        holder.txtTotal?.text = mListOrder[position].mTotal.toString()
        holder.txtCount?.text = (mListOrder[position].mAmount).toString()

        insertTotal()
        Picasso.with(mContext).load(mListOrder[position].mIceCream.mListImage[0])
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)

        /*handle click in plus and minus*/
        holder.btnPlus?.setOnClickListener {
            if (mCount < 99) {
                mCount += 1
                mListOrder[position].mAmount = mCount
                mListOrder[position].mTotal = mListOrder[position].mIceCream.price * mCount
                insertTotal()
                notifyDataSetChanged()
            }
        }

        holder.btnMinus?.setOnClickListener {
            if (mCount > 1) {
                mCount -= 1
                mListOrder[position].mAmount = mCount
                mListOrder[position].mTotal = mListOrder[position].mIceCream.price * mCount
                insertTotal()
                notifyDataSetChanged()
            } else {
                val mDialog = KAlertDialog(mContext, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Delete this file")
                    .setConfirmText("Yes,delete it!")
                mDialog.setConfirmClickListener {
                    CommonUtils.instace.getOrderList()?.removeAt(position)
                    mDialog.dismiss()
                    if (CommonUtils.instace.getOrderList()?.size == 0) {
                        mCallBack.onReturn()
                    }
                    notifyDataSetChanged()
                }.show()


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

    private fun insertTotal() {
        var mListPrice = ArrayList<Int>()
        for (i in 0 until mListOrder.size) {
            mListPrice.add(mListOrder[i].mTotal)
        }
        showTotalOnView(mListPrice)
    }

    private fun showTotalOnView(mListPrice: ArrayList<Int>) {
        var mTotal = 0
        for (i in 0 until mListPrice.size) {
            mTotal += mListPrice[i]
        }
        mCallBack.showTotal(mTotal)
    }

}