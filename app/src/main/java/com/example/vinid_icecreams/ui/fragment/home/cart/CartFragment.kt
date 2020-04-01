package com.example.vinid_icecreams.ui.fragment.home.cart


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
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.ui.fragment.home.pay.FragmentPay
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.btnCartPayment
import javax.inject.Inject

class CartFragment : BaseFragment<CartViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CartViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(CartViewModel::class.java)
    }

    override fun providerViewModel(): CartViewModel = viewModel

    private val cartController: CartController by lazy {
        CartController(
            viewModel,
            ::showDialog,
            ::showTotalPrice
        )
    }

    private var listOrder: ArrayList<Order>? = null
    private var storeSelected : Store? = null

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
        storeSelected = viewModel.getStoreSelection()
        setupBackDevice()
        setupRecycleView()
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
        listOrder = viewModel.getListOrder()
        cartController.listOrder = listOrder
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgCartBack -> {
                    findNavController().navigate(R.id.fragmentShopping)
                }
                R.id.btnCartPayment -> {
                    if (storeSelected?.range != 0.0) {
                        handleGetFeeShip()
                        showDiaLogPay()
                    }
                }
            }
        }
    }

    private fun handleGetFeeShip() {
        val location = viewModel.getCurrentLocation()
        if (location != null) {
            addressBill = Coordinates(
                location.latitude,
                location.longitude
            )
            showDiaLogPay()
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
        listOrder?.forEach {
            listItemBill.add(Item(it.iceCream.id,it.amount))
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
            viewModel.getListOrder().removeAt(position)
            mDialog.dismiss()
            if (viewModel.getListOrder().size == 0) {
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