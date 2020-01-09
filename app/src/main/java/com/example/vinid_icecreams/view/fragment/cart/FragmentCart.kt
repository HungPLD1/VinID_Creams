package com.example.vinid_icecreams.view.fragment.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterOrder.AdapterOrder
import com.example.vinid_icecreams.view.adapter.adapterOrder.OnItemOrderListener
import com.example.vinid_icecreams.view.fragment.pay.FragmentPay

class FragmentCart : Fragment(), View.OnClickListener, OnItemOrderListener {
    private var mLocationManager: LocationManager? = null

    private var mRcvOrder: RecyclerView? = null
    private var mAdapterOrder: AdapterOrder? = null
    private var mListOrder: ArrayList<Order>? = null
    private var mBtnBack: ImageView? = null
    private var mBtnPayment: Button? = null
    private var mTxtTotalPayment: TextView? = null
    private var mStoreSelected = CommonUtils.instace.getStoreSelected()

    companion object{
        var TAG = FragmentCart::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_cart, container, false)
        ProgressLoading.dismiss()
        initView(view)
        setupListOrder()
        return view
    }

    private fun setupListOrder() {
        mListOrder = CommonUtils.instace.getOrderList()
        if (mListOrder != null) {
            mAdapterOrder = AdapterOrder(context, mListOrder!!, this)
            mRcvOrder?.layoutManager = LinearLayoutManager(context)
            mRcvOrder?.adapter = mAdapterOrder
        }
    }

    @SuppressLint("MissingPermission")
    private fun initView(view: View?) {
        mRcvOrder = view?.findViewById(R.id.rcvOrder)
        mBtnBack = view?.findViewById(R.id.imgBack)
        mBtnPayment = view?.findViewById(R.id.btn_payment)
        mTxtTotalPayment = view?.findViewById(R.id.txt_total_payment)

        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?


        mBtnBack?.setOnClickListener(this)
        mBtnPayment?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgBack -> {
                    activity?.onBackPressed()
                }
                R.id.btn_payment -> {
                    if (mStoreSelected?.range != 0.0) {
                        showDiaLogPay()
                    } else {
                        if (CommonUtils.instace.checkPermission(
                                context!!,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            )
                        ) {
                            ProgressLoading.show(context)
                            handleGetLocation()
                        } else {
                            handleRequestPermission()
                        }
                    }

                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun handleGetLocation() {
        val location = mLocationManager?.getLastKnownLocation( LocationManager.NETWORK_PROVIDER)
        if (location != null){
            val mRange = CommonUtils.instace.calculationByDistance(
                location.latitude,
                location.longitude,
                mStoreSelected!!.latitude,
                mStoreSelected!!.longitude
            )
            mStoreSelected?.range = mRange
            CommonUtils.instace.saveStoreSelected(mStoreSelected!!)
            showDiaLogPay()
            ProgressLoading.dismiss()
        }else{
            ProgressLoading.dismiss()
        }
    }

    override fun onReturn() {
        activity?.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    override fun showTotal(total: Int) {
        CommonUtils.instace.setTotalPayment(total)
        mTxtTotalPayment?.text = "$total $"
    }

    //handle request permission
    private fun handleRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(permissions, CommonUtils.instace.REQUEST_CODE_PEMISSION)
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CommonUtils.instace.REQUEST_CODE_PEMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                   ProgressLoading.show(context)
                    handleGetLocation()
                }
            }
            else -> {

            }
        }
    }

    private fun showDiaLogPay() {
        val fragmentPay = FragmentPay()
        fragmentPay.isCancelable = false
        fragmentManager?.let { fragmentPay.show(it, null) }
    }

}