package com.example.vinid_icecreams.ui.fragment.home.details

import com.airbnb.epoxy.EpoxyController
import com.example.vinid_icecreams.model.Comment
import com.example.vinid_icecreams.ui.fragment.home.details.model.CommentHolderView
import com.example.vinid_icecreams.ui.fragment.home.details.model.commentHolderView

class DetailsController : EpoxyController() {

    var listComment: ArrayList<Comment>? = null
        set(value) {
            field = value
            requestModelBuild()
        }

    override fun buildModels() {
        listComment?.forEach {
            commentHolderView {
                id(CommentHolderView.TAG)
                comment(it)
            }
        }
    }
}
