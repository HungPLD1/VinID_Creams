package com.example.vinid_icecreams.view.adapter.adapterStore

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store

class AdapterStore(
    var mContext: Context?,
    var mListStore: ArrayList<Store>,
    var clicklistener: OnItemStoreClicklistener
) : RecyclerView.Adapter<AdapterStore.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_store, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListStore.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val status = mListStore.get(position).status
        /*
        0 : store is close
        1 : store is open
        */
        when(status){
            0 -> holder.imgStatusStore!!.setImageResource(R.drawable.ic_close)
            1 -> holder.imgStatusStore!!.setImageResource(R.drawable.ic_open)
        }

        holder.txtNameStore!!.text = mListStore.get(position).name
        holder.txtAddressStore!!.text = mListStore.get(position).address

        /*handle click on item Store*/
        holder.rawStore!!.setOnClickListener {
            clicklistener.onItemClick(position)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgStatusStore : ImageView? = null
        var txtNameStore: TextView? = null
        var txtAddressStore: TextView? = null
        var rawStore : LinearLayout? = null

        init {
          imgStatusStore = itemView.findViewById(R.id.imgStatusStore)
            txtNameStore = itemView.findViewById(R.id.txtNameStore)
            txtAddressStore = itemView.findViewById(R.id.txtAddress)
            rawStore = itemView.findViewById(R.id.rawStore)
        }
    }
}