package com.example.vinid_icecreams.ui.fragment.event

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.ui.fragment.event.model.eventView

class EventController(
    private val onClickEvent: (index :Int) -> Unit
) : EpoxyController() {

    var listEvent: ArrayList<Event>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listEvent?.forEachIndexed { index, event ->
            eventView {
                id(event.id)
                event(event)
                onClickEventListener { onClickEvent(index) }
            }
        }
    }

}