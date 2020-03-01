package com.example.vinid_icecreams.view.fragment.home.store.model

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.airbnb.epoxy.AfterPropsSet
import com.airbnb.epoxy.CallbackProp
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_layout_store.view.*
import java.text.DecimalFormat

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class StoreHolderView @JvmOverloads constructor(
    context: Context,
    attrs : AttributeSet? = null,
    defStyle : Int = 0
):LinearLayout(context,attrs,defStyle){

    var onClickStoreListener : ((View?) -> Unit)? = null
    @CallbackProp set

    var store : Store? = null
    @ModelProp set

    init {
        View.inflate(context, R.layout.raw_layout_store,this)
        orientation = VERTICAL
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind(){
        txtNameStore?.text = store?.name
        txtStoreAddress.text = store?.address
        Picasso.with(context).load(store?.imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(imgStore)

        /*set store ranger*/
        if (store?.range != 0.0){
            val newFormat = DecimalFormat("###.#")
            val rangeInDec: String = String.format(newFormat.format(store?.range))
            txtStoreRange?.text = "Cách $rangeInDec km"
        }

        /*handle click on item store*/
        setOnClickListener {
            onClickStoreListener?.invoke(it)
        }
    }

    companion object{
        var TAG = StoreHolderView::class.java.name
    }
}