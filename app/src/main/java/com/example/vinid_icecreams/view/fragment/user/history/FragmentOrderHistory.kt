package com.example.vinid_icecreams.view.fragment.user.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.OrderInfor
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterHistoryOrder.AdapterOrderHistory
import com.example.vinid_icecreams.view.adapter.adapterHistoryOrder.OnClickItemOrderHistory
import com.example.vinid_icecreams.view.fragment.user.HomeUser
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentOrderHistory : Fragment(), OnClickItemOrderHistory, View.OnClickListener {


    private var mBtnBackToUser: ImageView? = null
    private var rcvOrderHistory: RecyclerView? = null
    private var mListOrderInfor: ArrayList<OrderInfor>? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeData()
        handleGetListHistory()
    }

    private fun initView(view: View) {
        rcvOrderHistory = view.findViewById(R.id.rcv_order)
        mBtnBackToUser = view.findViewById(R.id.img_back_to_user)
        mBtnBackToUser?.setOnClickListener(this)
    }

    private fun observeData() {
        mViewModel.mListOrderInfor.observe(viewLifecycleOwner, Observer { data ->
            mListOrderInfor = data
            setupListOrder(data)
            ProgressLoading.dismiss()
        })

    }

    private fun handleGetListHistory() {
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getOrderUser()
        } else {
            showNoConnection()
        }
    }

    private fun setupListOrder(mListOrderInfor: ArrayList<OrderInfor>) {
        val mAdapter = AdapterOrderHistory(context, mListOrderInfor, this)
        rcvOrderHistory?.layoutManager = LinearLayoutManager(context)
        rcvOrderHistory?.adapter = mAdapter
    }

    override fun onItemClick(positon: Int) {
        val bundle = bundleOf("ID" to mListOrderInfor!![positon].id)
        this.findNavController().navigate(R.id.fragmentOrderDetails,bundle)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.img_back_to_user -> {
                    findNavController().navigate(R.id.fragmentProfile)
                }
            }
        }
    }

    private fun showNoConnection() {
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
                handleGetListHistory()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}