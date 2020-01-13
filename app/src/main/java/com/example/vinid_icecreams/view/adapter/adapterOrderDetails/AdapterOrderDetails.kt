package com.example.vinid_icecreams.view.adapter.adapterOrderDetails

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.ItemOrder
import com.squareup.picasso.Picasso


class AdapterOrderDetails(
    private var mContext: Context?,
    private var mListItemOrder: ArrayList<ItemOrder>?,
    private var mCallBack : OnItemDetailsHistoryClicklistener?
) : RecyclerView.Adapter<AdapterOrderDetails.MyViewHolder>() {
    private var mStar = 5
    private var mComment = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView =
            LayoutInflater.from(mContext).inflate(R.layout.raw_layout_order_details, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListItemOrder!!.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtNameOrder?.text = mListItemOrder?.get(position)?.iceCreamInfo?.name
        holder.txtPriceOrder?.text = mListItemOrder?.get(position)?.iceCreamInfo?.price.toString() + " $"
        holder.txtCountOrder?.text = "x " + mListItemOrder?.get(position)?.quantity.toString()
        holder.txtTotalOrder?.text = "= " +
            (mListItemOrder?.get(position)?.iceCreamInfo?.price?.times(mListItemOrder?.get(position)?.quantity!!)).toString() + " $"

        Picasso.with(mContext).load(mListItemOrder?.get(position)?.iceCreamInfo?.imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(holder.imgOrder)

        if (mListItemOrder?.get(position)?.iceCreamInfo?.ratting == 0){
            holder.btnShowRating?.visibility = View.VISIBLE
        }else{
            Log.d("AdapterOrderDetails", mListItemOrder?.get(position)?.iceCreamInfo?.ratting.toString())
            holder.btnShowRating?.visibility = View.GONE
        }

        /*handle click*/
        holder.btnShowRating?.setOnClickListener {
            if (holder.lnContainerRating!!.isVisible){
                holder.lnContainerRating?.visibility = View.GONE
            }else{
                holder.lnContainerRating?.visibility = View.VISIBLE
            }
        }

        holder.ratingIceCream?.setOnRatingBarChangeListener{ _: RatingBar, star: Float, _: Boolean ->
            mStar = star.toInt()
        }

        holder.btnSubmit?.setOnClickListener {
            val id = mListItemOrder?.get(position)?.iceCreamInfo?.id
            mComment = holder.edtContent?.text.toString()
            mCallBack?.onItemSubmit(id ,mStar,mComment)
        }
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtNameOrder: TextView? = null
        var txtCountOrder: TextView? = null
        var imgOrder: ImageView? = null
        var txtTotalOrder: TextView? = null
        var txtPriceOrder: TextView? = null
        var btnShowRating : LinearLayout? = null
        var lnContainerRating : LinearLayout? = null

        var ratingIceCream : RatingBar? = null
        var btnSubmit : Button? = null
        var edtContent : EditText? = null

        init {
            txtNameOrder = itemView.findViewById(R.id.txt_history_details_name)
            txtCountOrder = itemView.findViewById(R.id.txt_history_details_count)
            imgOrder = itemView.findViewById(R.id.img_history_details_order)
            txtTotalOrder = itemView.findViewById(R.id.txt_history_details_total)
            txtPriceOrder = itemView.findViewById(R.id.txt_history_details_price)
            btnShowRating = itemView.findViewById(R.id.ln_show_rating)
            lnContainerRating = itemView.findViewById(R.id.ln_container_rating)

            ratingIceCream = itemView.findViewById(R.id.rtb_order_details)
            btnSubmit = itemView.findViewById(R.id.btn_order_details_submit)
            edtContent = itemView.findViewById(R.id.edt_order_details_content)
        }
    }
}



