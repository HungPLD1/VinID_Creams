package com.example.vinid_icecreams.ui.fragment.user.history.model

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
import com.example.vinid_icecreams.model.OrderInfor
import kotlinx.android.synthetic.main.raw_order_history.view.*

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class OrderHistoryItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    var onClickDetailsListener: ((View?) -> Unit)? = null
        @CallbackProp set

    var orderInfor: OrderInfor? = null
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_order_history, this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind() {
        txtHistoryTime.text = orderInfor?.createAt
        txtHistoryTotal.text = orderInfor?.totalFee.toString() + " $"
        txtHistoryShip.text = orderInfor?.shipFee.toString() + " $"

        if (orderInfor?.status == 0){
            txtHistoryPayment.text = "Giao hàng nhận tiền"
        }else{
            txtHistoryPayment.text = "Point"
        }

        txtHistoryDetails.setOnClickListener{
            onClickDetailsListener?.invoke(it)
        }
    }

    companion object {
        var TAG = OrderHistoryItemView::class.java.name
    }
}