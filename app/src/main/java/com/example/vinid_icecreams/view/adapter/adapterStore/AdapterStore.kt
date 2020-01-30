package com.example.vinid_icecreams.view.adapter.adapterStore

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyModelClass
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

@EpoxyModelClass(layout = R.layout.raw_layout_store)
class AdapterStore (
    var mContext: Context?,
    var mListStore: ArrayList<Store>,
    private var callback: OnItemStoreClicklistener
) : RecyclerView.Adapter<AdapterStore.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_layout_store, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListStore.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNameStore?.text = mListStore[position].name
        holder.txtAddressStore?.text = mListStore[position].address
        Picasso.with(mContext).load(mListStore[position].imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgStore)

        if (mListStore[position].range != 0.0){
            val newFormat = DecimalFormat("###.#")
            val rangeInDec: String = String.format(newFormat.format(mListStore[position].range))
            holder.txtRangeStore?.text = "Cách $rangeInDec km"
        }

        /*handle click on item Store*/

        holder.itemView.setOnClickListener{
            callback.onItemClick(position)
        }

    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgStore : ImageView? = null
        var txtNameStore: TextView? = null
        var txtAddressStore: TextView? = null
        var txtRangeStore : TextView? = null

        init {
          imgStore = itemView.findViewById(R.id.img_raw_store)
            txtNameStore = itemView.findViewById(R.id.txt_raw_name_store)
            txtAddressStore = itemView.findViewById(R.id.txt_raw_store_address)
            txtRangeStore = itemView.findViewById(R.id.txt_raw_store_range)
        }
    }
}