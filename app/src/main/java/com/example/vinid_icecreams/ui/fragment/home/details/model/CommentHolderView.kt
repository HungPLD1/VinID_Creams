package com.example.vinid_icecreams.ui.fragment.home.details.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Comment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_comment.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CommentHolderView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyle : Int = 0
): CardView(context,attrs,defStyle) {

    var onClickStoreListener : ((View?) -> Unit)? = null
        @CallbackProp set

    var comment : Comment? = null
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_comment,this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind(){
        txtNameUserComment?.text = comment?.user_name
        if (comment?.rating_star?.toFloat() != null){
            rattingRawComment?.rating = comment?.rating_star?.toFloat()!!
        }else{
            rattingRawComment?.rating = 5.0F
        }
        txtRawCommentContent?.text = comment?.comment
        Picasso.with(context).load(comment?.user_avatar)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_avatar)
            .into(imgRawComment)
    }

    companion object{
        var TAG = CommentHolderView::class.java.name
    }
}