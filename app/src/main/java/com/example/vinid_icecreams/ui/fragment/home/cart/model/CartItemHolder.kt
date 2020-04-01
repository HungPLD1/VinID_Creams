package com.example.vinid_icecreams.ui.fragment.home.cart.model

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
import com.example.vinid_icecreams.model.Order
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_layout_cart.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class CartItemHolder @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyle : Int = 0
): CardView(context,attrs,defStyle){

    lateinit var order: Order
        @ModelProp set

    var cartItemListener : Listener? = null
        @CallbackProp set

    var count : Int = 0
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_layout_cart,this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind(){
        txtNameOrder.text = order.iceCream.name
        txtPriceOrder.text = order.iceCream.price.toString()+ " $"
        order.total = order.iceCream.price!! * count
        txtTotal.text = order.total.toString() + " $"
        txtCount.text = order.amount.toString()
        cartItemListener?.onInsertTotal()
        Picasso.with(context).load(order.iceCream.image_paths?.get(0))
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(imgOrder)
        handleClickPlusAndMinus()
    }

    /*handle click in plus and minus*/
    private fun handleClickPlusAndMinus() {
        btnPlus.setOnClickListener {
            cartItemListener?.onClickPlus()
        }

        btnMinus.setOnClickListener {
            cartItemListener?.onClickMinus()
        }
    }

    interface Listener {
        fun onInsertTotal ()
        fun onClickPlus()
        fun onClickMinus()
    }

    companion object{
        var TAG = CartItemHolder::class.java.name
    }
}