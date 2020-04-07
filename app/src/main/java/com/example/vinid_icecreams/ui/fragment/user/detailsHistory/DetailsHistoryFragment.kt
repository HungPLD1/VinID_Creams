package com.example.vinid_icecreams.ui.fragment.user.detailsHistory

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseDialogFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import kotlinx.android.synthetic.main.dialog_order_history_details.*
import javax.inject.Inject

class DetailsHistoryFragment : BaseDialogFragment<DetailsHistoryViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var orderID: Int = 0

    private val viewModel: DetailsHistoryViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailsHistoryViewModel::class.java)
    }

    override fun providerViewModel(): DetailsHistoryViewModel = viewModel

    private val detailsItemController: DetailsHistoryController by lazy {
        DetailsHistoryController(
            ::submitRating
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        isCancelable = false
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_order_history_details, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        getOrderID()
        handleEventClick()
        setUpListOrderInfo()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.listItemOrder.observe(viewLifecycleOwner, Observer { data ->
            detailsItemController.listItemOrder = data
        })
        viewModel.isRating.observe(viewLifecycleOwner, Observer { isRating ->
            if (isRating) {
                dismiss()
                showDialogSuccess("Ratting success","Đánh giá thành công")
            } else {
                showDiaLogFailed("Rating Error", messageFailed)
            }
        })

        getListItemOrder()
    }

    private fun getOrderID() {
        orderID = arguments?.getInt("ID")!!
    }

    private fun getListItemOrder() {
        if (isConnectToNetwork(context)) {
            viewModel.getListItemOrder(orderID)
        } else {
            showNoConnection()
        }
    }

    private fun handleEventClick() {
        btnClose.setOnClickListener(this)
    }

    private fun setUpListOrderInfo() {
        rcvDetailsOrder.run {
            setItemSpacingDp(4)
            setController(detailsItemController)
        }
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                btnClose -> dismiss()
            }
        }
    }

    private fun submitRating(id: Int?, rating: Int?, comment: String?) {
        Toast.makeText(
            context,
            id.toString() + "-" + rating.toString() + "-" + comment.toString(),
            Toast.LENGTH_SHORT
        ).show()

        if (isConnectToNetwork(context)) {
            viewModel.setRatingItem(id, rating, comment)
        } else {
            showNoConnection()
        }
    }
}