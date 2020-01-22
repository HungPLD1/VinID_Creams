package com.example.vinid_icecreams.view.fragment.user.history

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.ItemOrder
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterOrderDetails.AdapterOrderDetails
import com.example.vinid_icecreams.view.adapter.adapterOrderDetails.OnItemDetailsHistoryClicklistener
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentOrderDetails : DialogFragment(), View.OnClickListener,
    OnItemDetailsHistoryClicklistener {
    private var orderID: Int = 0
    private var mMessageFail : String? = null

    private var mRcvIteminfo: RecyclerView? = null
    private var mBtnClose: ImageView? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        getOrderID()
        observeData()
        handleGetListItemInfo()
    }

    private fun getOrderID() {
        orderID = arguments?.getInt("ID")!!
    }

    /*observe data*/
    private fun observeData() {
        mViewModel.mListItemOrder.observe(viewLifecycleOwner, Observer { data ->
            setUpListOrderInfo(data)
            ProgressLoading.dismiss()
        })
        mViewModel.mIsRating.observe(viewLifecycleOwner, Observer { isRating ->
            if (isRating) {
                ProgressLoading.dismiss()
                dismiss()
                rattingSuccess()
            } else {
                showRatingFail()
            }
        })
        mViewModel.mMessageFail.observe(viewLifecycleOwner, Observer {
            mMessageFail = it
        })
    }

    private fun handleGetListItemInfo() {
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getListItemInfo(orderID)
        } else {
            showNoConnection()
        }
    }

    private fun initView(view: View) {
        mRcvIteminfo = view.findViewById(R.id.rcv_details_order_history)
        mBtnClose = view.findViewById(R.id.img_close_details)

        mBtnClose?.setOnClickListener(this)
    }

    private fun setUpListOrderInfo(mData: ArrayList<ItemOrder>?) {
        val mAdapter = AdapterOrderDetails(context, mData, this)
        mRcvIteminfo?.adapter = mAdapter
        mRcvIteminfo?.layoutManager = LinearLayoutManager(context)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.img_close_details -> dismiss()
            }
        }
    }

    private fun showNoConnection() {
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    override fun onItemSubmit(id: Int?, rating: Int?, comment: String?) {
        Toast.makeText(
            context,
            id.toString() + "-" + rating.toString() + "-" + comment.toString(),
            Toast.LENGTH_SHORT
        ).show()

        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.setRatingItem(id, rating, comment)
        } else {
            showNoConnection()
        }
    }

    /*login success*/
    private fun rattingSuccess() {
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Ratting success")
            .show()
    }

    private fun showRatingFail() {
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText("Rating Error")
            .setContentText(mMessageFail)
            .show()
    }
}