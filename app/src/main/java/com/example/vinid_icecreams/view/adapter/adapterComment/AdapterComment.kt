package com.example.vinid_icecreams.view.adapter.adapterComment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Comment
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AdapterComment(
    var mContext: Context?,
    var mListComment: ArrayList<Comment>
) : RecyclerView.Adapter<AdapterComment.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val mView = LayoutInflater.from(mContext).inflate(R.layout.raw_comment, parent, false)
        return MyViewHolder(mView)
    }

    override fun getItemCount(): Int {
        return mListComment.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.txtName?.text = mListComment[position].mNameUser
        holder.rattingComment?.rating = mListComment[position].mRating
        holder.txtContentComment?.text = mListComment[position].mConTent
        Picasso.with(mContext).load(mListComment[position].mImageUser)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_avata)
            .into(holder.imgComment)
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtName : TextView? = null
        var imgComment: CircleImageView? = null
        var rattingComment :  RatingBar? = null
        var txtContentComment : TextView? = null

        init {
            txtName = itemView.findViewById(R.id.txt_NameUserComment)
            imgComment = itemView.findViewById(R.id.img_Comment)
            rattingComment = itemView.findViewById(R.id.ratting_Comment)
            txtContentComment = itemView.findViewById(R.id.txt_ContentComment)
        }
    }
}