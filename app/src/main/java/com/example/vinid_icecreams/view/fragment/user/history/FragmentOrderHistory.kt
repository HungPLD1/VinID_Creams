package com.example.vinid_icecreams.view.fragment.user.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.OrderInfor
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterHistoryOrder.AdapterOrderHistory
import com.example.vinid_icecreams.view.adapter.adapterHistoryOrder.OnClickItemOrderHistory
import com.example.vinid_icecreams.view.fragment.home.pay.FragmentPay
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentOrderHistory : Fragment(), OnClickItemOrderHistory {
    private var rcvOrderHistory : RecyclerView? = null
    private var mListOrderInfor : ArrayList<OrderInfor>? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_history,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeData()
    }

    private fun initView(view: View) {
        rcvOrderHistory = view.findViewById(R.id.rcv_order)

    }

    private fun observeData(){
        ProgressLoading.show(context)
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            mViewModel.getOrderUser()
            mViewModel.mListOrderInfor.observe(this, Observer { data ->
                mListOrderInfor = data
                setupListOrder(mListOrderInfor)
                ProgressLoading.dismiss()
            })
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    private fun setupListOrder(mListOrderInfor: ArrayList<OrderInfor>) {
        val mAdapter =  AdapterOrderHistory(context,mListOrderInfor,this)
        rcvOrderHistory?.layoutManager = LinearLayoutManager(context)
        rcvOrderHistory?.adapter = mAdapter
    }

    override fun onItemClick(positon: Int) {
        val bundle = Bundle()
        val fragmentDetailsHistory = FragmentOrderDetails()
        bundle.putInt("ID", mListOrderInfor!![positon].id)
        fragmentDetailsHistory.arguments = bundle
        fragmentDetailsHistory.isCancelable = false
        fragmentManager?.let { fragmentDetailsHistory.show(it, null) }
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                observeData()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}