package com.example.vinid_icecreams.view.adapter.adapterCart

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.squareup.picasso.Picasso


class AdapterOrder(
    private var mContext: Context?,
    private var mListOrder: ArrayList<Order>,
    private var mCallBack : OnItemOrderListener
) : RecyclerView.Adapter<AdapterOrder.MyViewHolder>() {
    private var mTotal = MutableLiveData<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_cart, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListOrder.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var mCount = mListOrder[position].mAmount

        holder.txtNameOrder?.text = mListOrder[position].mNameOrder
        holder.txtPriceOrder?.text = mListOrder[position].mPrice.toString()
        holder.txtTotal?.text = (mListOrder[position].mPrice * mCount).toString()
        holder.txtCount?.text = (mListOrder[position].mAmount).toString()

        Picasso.with(mContext).load(mListOrder[position].mImageOrder)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)

        mTotal.value = mListOrder[position].mPrice * mCount

        /*handle click in plus and minus*/

        holder.btnPlus?.setOnClickListener {
            if (mCount < 99) {
                mCount += 1
                mListOrder[position].mAmount = mCount
                Log.d("Mcount", mListOrder[position].mAmount.toString())
                notifyDataSetChanged()
            }
        }

        holder.btnMinus?.setOnClickListener {
            if (mCount > 1){
                mCount -= 1
                mListOrder[position].mAmount = mCount
                notifyDataSetChanged()
            }else{
                val mDialog = KAlertDialog(mContext, KAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Delete this file")
                    .setConfirmText("Yes,delete it!")
                    mDialog.setConfirmClickListener {
                        CommonUtils.instace.getOrderList()?.removeAt(position)
                        mDialog.dismiss()
                        if (CommonUtils.instace.getOrderList()?.size == 0){
                            mCallBack.onReturn()
                        }
                        notifyDataSetChanged()
                    }
                    .show()


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

    fun getTotal():MutableLiveData<Int>{
        return mTotal
    }

}