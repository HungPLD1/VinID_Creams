package com.example.vinid_icecreams.ui.fragment.home.cart

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.repository.remote.requestBody.Coordinates
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.repository.remote.requestBody.Item
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.ui.adapter.adapterOrder.AdapterOrder
import com.example.vinid_icecreams.ui.adapter.adapterOrder.OnItemOrderListener
import com.example.vinid_icecreams.ui.fragment.home.pay.FragmentPay
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.btnCartPayment

class FragmentCart : Fragment(), View.OnClickListener, OnItemOrderListener {
    private var mLocationManager: LocationManager? = null

    private var mAdapterOrder: AdapterOrder? = null
    private var mListOrder: ArrayList<Order>? = null
    private var mStoreSelected = CommonUtils.instace.getStoreSelected()

    private var addressBill  : Coordinates? = null
    private var totalBill : Int? = null
    private var listItemBill = ArrayList<Item>()
    private var bill : Bill? = null

    companion object{
        var TAG = FragmentCart::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupBackDevice()
        setupListOrder()
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentShopping)
        }
    }

    private fun setupListOrder() {
        mListOrder = CommonUtils.instace.getOrderList()
        if (mListOrder != null) {
            mAdapterOrder = AdapterOrder(context, mListOrder!!, this)
            rcvCartOrder?.layoutManager = LinearLayoutManager(context)
            rcvCartOrder?.adapter = mAdapterOrder
        }
    }

    @SuppressLint("MissingPermission")
    private fun initView() {
        mLocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?


        imgCartBack?.setOnClickListener(this)
        btnCartPayment?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgCartBack -> {
                    findNavController().navigate(R.id.fragmentShopping)
                }
                R.id.btnCartPayment -> {
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
            addressBill = Coordinates(
                location.latitude,
                location.longitude
            )
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
            KAlertDialog(activity, KAlertDialog.WARNING_TYPE)
                .setTitleText("Location error")
                .setContentText("Không xác nhận được địa chỉ của bạn")
                .show()
        }
    }

    override fun onReturn() {
        activity?.onBackPressed()
    }

    @SuppressLint("SetTextI18n")
    override fun showTotal(total: Int) {
        totalBill = total
        CommonUtils.instace.setTotalPayment(total)
        txtCartTotalPayment?.text = "$total $"
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
        addDataToBill()
        val bundle = Bundle()
        val fragmentPay = FragmentPay()
        bundle.putSerializable("BILL", bill)
        fragmentPay.arguments = bundle
        fragmentPay.isCancelable = false
        fragmentManager?.let { fragmentPay.show(it, null) }
    }


    private fun addDataToBill(){
        for(i in 0 until mListOrder!!.size){
            listItemBill.add(
                Item(
                    mListOrder!![i].mIceCream.id,
                    mListOrder!![i].mAmount
                )
            )
        }
        bill = Bill(
            0,
            addressBill,
            0,
            totalBill,
            listItemBill
        )
        
    }
}