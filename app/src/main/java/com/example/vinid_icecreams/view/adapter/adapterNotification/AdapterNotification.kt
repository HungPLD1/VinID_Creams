package com.example.vinid_icecreams.view.adapter.adapterNotification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Event
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AdapterNotification(
    private var mContext: Context?,
    private var mListNotification: ArrayList<Event>,
    private var mCallBack: OnItemNotificationClicklistener
) : RecyclerView.Adapter<AdapterNotification.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView =
            LayoutInflater.from(mContext).inflate(R.layout.raw_notifilecation, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListNotification.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.txtTitle?.text = mListNotification[position].mTitle
        holder.txtContentNotify?.text = mListNotification[position].mContent
        Picasso.with(mContext).load(mListNotification[position].mImage)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.ic_bell)
            .into(holder.imgNotify)

        holder.lnContainer?.setOnClickListener {
            mCallBack.onItemClick(position)
        }


    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView? = null
        var imgNotify: CircleImageView? = null
        var txtContentNotify: TextView? = null
        var lnContainer : LinearLayout? = null

        init {
            txtTitle = itemView.findViewById(R.id.txt_title_noti)
            imgNotify = itemView.findViewById(R.id.img_noti)
            txtContentNotify = itemView.findViewById(R.id.txt_content_noti)
            lnContainer = itemView.findViewById(R.id.notification_container)
        }
    }
}