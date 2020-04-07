package com.example.vinid_icecreams.ui.fragment.user.detailsHistory.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.RatingBar
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.ItemOrder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_wallet.view.*
import kotlinx.android.synthetic.main.raw_layout_order_details.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class ItemOrderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    private var starRating = 5

    private var comment = ""

    var listener: ItemOrderListener? = null
        @CallbackProp set

    lateinit var itemOrder: ItemOrder
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_layout_order_details, this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind() {
        Log.d("hungpld1",itemOrder.toString())
        txtName.text = itemOrder.iceCreamInfo.name
        txtPrice.text = itemOrder.iceCreamInfo.price.toString() + " $"
        txtCount.text = "x " + itemOrder.quantity.toString()
        txtTotal.text = "= " +
                (itemOrder.iceCreamInfo.price * itemOrder.quantity).toString() + " $"

        Picasso.with(context).load(itemOrder.iceCreamInfo.imagePath[0])
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(imgOrder)

        if (itemOrder.iceCreamInfo.ratting == 0){
            viewShowRating.visibility = View.VISIBLE
        }else{
            viewShowRating.visibility = View.GONE
        }

        /*handle click*/
        viewShowRating.setOnClickListener {
            if (lnContainerRating.isVisible){
                lnContainerRating.visibility = View.GONE
            }else{
                lnContainerRating.visibility = View.VISIBLE
            }
        }



        rtbItem.setOnRatingBarChangeListener{ _: RatingBar, star: Float, _: Boolean ->
            starRating = star.toInt()
        }

        btnSubmitRating.setOnClickListener {
            val id = itemOrder.iceCreamInfo.id
            comment = edtContentRating.text.toString()
            listener?.onSubmit(id ,starRating,comment)
        }
    }

    companion object {
        var TAG = ItemOrderView::class.java.name
    }

    interface ItemOrderListener{
        fun onSubmit(id: Int, starRating: Int, comment: String)
    }
}