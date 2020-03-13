package com.example.vinid_icecreams.ui.fragment.home.shopping.model

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
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.utils.CommonUtils
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_layout_ice_cream.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class IceCreamHolderView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyle : Int = 0
): CardView(context,attrs,defStyle){

    var onClickStoreListener : ((View?) -> Unit)? = null
        @CallbackProp set

    var iceCream : IceCream? = null
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_layout_ice_cream,this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind(){
        var type = iceCream?.type
        /*
        0: Chocolate
        1: Matcha
        2: Strawberry
        3: Cacao
        4: Vani
        5: Other
        6: Mix
        */
        Picasso.with(context).load(iceCream?.image_paths?.get(0))
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(imgIceCream)
        txtNameIceCream?.text = iceCream?.name
        txtPriceIceCream?.text = iceCream?.price.toString() + " $"

        val listComment = iceCream?.listComment.orEmpty()
        val listRatingBar = mutableListOf<Int>()
        for (element in listComment) {
            listRatingBar.add(element.rating_star)
        }
        rbIceCream.rating = CommonUtils.instace.calculateAverage(listRatingBar)

        /*handle click on item ice-cream*/
        setOnClickListener {
            onClickStoreListener?.invoke(it)
        }

    }

    companion object{
        var TAG = IceCreamHolderView::class.java.name
    }
}