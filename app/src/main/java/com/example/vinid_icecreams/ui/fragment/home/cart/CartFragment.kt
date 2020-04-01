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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.repository.remote.requestBody.Coordinates
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.repository.remote.requestBody.Item
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.ui.fragment.home.pay.FragmentPay
import com.example.vinid_icecreams.ui.fragment.home.store.StoreController
import com.example.vinid_icecreams.utils.Const
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.btnCartPayment
import javax.inject.Inject

class CartFragment : BaseFragment<CartViewModel>()
    , View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun providerViewModel(): CartViewModel = viewModel

    private val viewModel: CartViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(CartViewModel::class.java)
    }

    private val mainViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private val cartController: CartController by lazy {
        CartController(
            mainViewModel,
            ::showDialog,
            ::showTotalPrice
        )
    }

    private var locationManager: LocationManager? = null

    private var listOrder: ArrayList<Order>? = null
    private var mStoreSelected = CommonUtils.instace.getStoreSelected()

    private var addressBill: Coordinates? = null
    private var totalBill: Int? = null
    private var listItemBill = ArrayList<Item>()
    private var bill: Bill? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        setupBackDevice()
        setupRecycleView()
        locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager?
        imgCartBack?.setOnClickListener(this)
        btnCartPayment?.setOnClickListener(this)
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentShopping)
        }
    }

    private fun setupRecycleView() {
        rcvCartOrder.run {
            setItemSpacingDp(3)
            setController(cartController)
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        listOrder = mainViewModel.getListOrder()
        cartController.listOrder = listOrder
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
                                requireContext(),
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
        val location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
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
        } else {
            ProgressLoading.dismiss()
            KAlertDialog(activity, KAlertDialog.WARNING_TYPE)
                .setTitleText("Location error")
                .setContentText("Không xác nhận được địa chỉ của bạn")
                .show()
        }
    }

    //handle request permission
    private fun handleRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(permissions, Const.REQUEST_CODE_PEMISSION)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Const.REQUEST_CODE_PEMISSION -> {
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


    private fun addDataToBill() {
        for (i in 0 until listOrder!!.size) {
            listItemBill.add(
                Item(
                    listOrder!![i].iceCream.id,
                    listOrder!![i].amount
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

    private fun showDialog(position: Int) {
        val mDialog = KAlertDialog(requireContext(), KAlertDialog.WARNING_TYPE)
            .setTitleText("Are you sure?")
            .setContentText("Delete this file")
            .setConfirmText("Yes,delete it!")
        mDialog.setConfirmClickListener {
            mainViewModel.getListOrder().removeAt(position)
            mDialog.dismiss()
            if (mainViewModel.getListOrder().size == 0) {
                activity?.onBackPressed()
            }
            cartController.requestModelBuild()
        }.show()
    }

    private fun showTotalPrice(totalPrice : Int){
        txtCartTotalPayment.text = totalPrice.toString()
    }

    companion object {
        var TAG = CartFragment::class.java.name
    }
}