package com.example.vinid_icecreams.view.adapter.adapterHistoryOrder

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.OrderInfor

class AdapterOrderHistory(
    var mContext: Context?,
    var mListOrderInfo: ArrayList<OrderInfor>,
    private var callback: OnClickItemOrderHistory
) : RecyclerView.Adapter<AdapterOrderHistory.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_order_history, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListOrderInfo.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtTimeOrder?.text = mListOrderInfo[position].createAt
        holder.txtFeeTotal?.text = mListOrderInfo[position].totalFee.toString() + " $"
        holder.txtFeeShip?.text = mListOrderInfo[position].shipFee.toString() + " $"

        if (mListOrderInfo[position].status == 0){
            holder.txtPayment?.text = "Giao hàng nhận tiền"
        }else{
            holder.txtPayment?.text = "Point"
        }

        holder.itemView.setOnClickListener{
            callback.onItemClick(position)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTimeOrder : TextView? = null
        var txtFeeTotal : TextView? = null
        var txtFeeShip : TextView? = null
        var txtPayment : TextView? = null


        init {
          txtTimeOrder = itemView.findViewById(R.id.txt_order_history_time)
            txtFeeTotal = itemView.findViewById(R.id.txt_order_history_total)
            txtFeeShip = itemView.findViewById(R.id.txt_order_history_ship)
            txtPayment = itemView.findViewById(R.id.txt_order_history_payment)
        }
    }
}

