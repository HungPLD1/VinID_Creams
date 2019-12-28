package com.example.vinid_icecreams.view.fragment.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterCart.AdapterOrder

class FragmentCart : Fragment(),View.OnClickListener {


    var mRcvOrder : RecyclerView? = null
    var mAdapterOrder : AdapterOrder? = null
    var mListOrder : ArrayList<Order>? = null
    var mBtnBack : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_cart,container,false)
        ProgressLoading.dismiss()
        initView(view)
        setupListOrder()
        return view
    }

    private fun setupListOrder() {
        mListOrder = CommonUtils.instace.getOrderList()
        if(mListOrder!= null){
            mAdapterOrder = AdapterOrder(context,mListOrder!!)
            mRcvOrder?.layoutManager = LinearLayoutManager(context)
            mRcvOrder?.adapter = mAdapterOrder
        }
    }

    private fun initView(view: View?) {
        mRcvOrder = view?.findViewById(R.id.rcvOrder)
        mBtnBack = view?.findViewById(R.id.imgBack)

        mBtnBack?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null){
            when(view.id){
                R.id.imgBack ->{
                    activity?.onBackPressed()
                }
            }
        }
    }
}