package com.example.vinid_icecreams.view.adapter.adapterStore

import android.annotation.SuppressLint
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
import com.squareup.picasso.Picasso

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


        holder.txtNameStore?.text = mListStore[position].name
        holder.txtAddressStore?.text = mListStore[position].address
        Picasso.with(mContext).load(mListStore[position].image)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgStore)
        /*handle click on item Store*/

        holder.itemView.setOnClickListener{
            clicklistener.onItemClick(position)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgStore : ImageView? = null
        var txtNameStore: TextView? = null
        var txtAddressStore: TextView? = null

        init {
          imgStore = itemView.findViewById(R.id.imgImageStore)
            txtNameStore = itemView.findViewById(R.id.txtNameStore)
            txtAddressStore = itemView.findViewById(R.id.txtAddress)
        }
    }
}