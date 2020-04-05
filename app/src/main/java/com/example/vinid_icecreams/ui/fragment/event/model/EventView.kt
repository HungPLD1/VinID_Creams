package com.example.vinid_icecreams.ui.fragment.event.model

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
import com.example.vinid_icecreams.model.Event
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.raw_notifilecation.view.*


@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class EventView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : CardView(context, attrs, defStyle) {

    var onClickEventListener: ((View?) -> Unit)? = null
        @CallbackProp set


    var event: Event? = null
        @ModelProp set

    init {
        View.inflate(context, R.layout.raw_notifilecation, this)
    }

    @SuppressLint("SetTextI18n")
    @AfterPropsSet
    fun bind() {
        txtEventTitle.text = event?.title
        txtEventContent.text = event?.content
        Picasso.with(context).load(event?.image)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.ic_bell)
            .into(imgEvent)

       setOnClickListener {
            onClickEventListener?.invoke(it)
        }
    }

    companion object {
        var TAG = EventView::class.java.name
    }
}