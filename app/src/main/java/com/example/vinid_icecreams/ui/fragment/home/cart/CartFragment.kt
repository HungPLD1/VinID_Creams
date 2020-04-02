package com.example.vinid_icecreams.ui.fragment.home.cart


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.DialogClickListener
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.repository.remote.requestBody.Coordinates
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.repository.remote.requestBody.Item
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.ui.fragment.home.cart.model.CartAdapter
import com.example.vinid_icecreams.ui.fragment.home.pay.FragmentPay
import kotlinx.android.synthetic.main.fragment_cart.*
import kotlinx.android.synthetic.main.fragment_cart.btnCartPayment
import javax.inject.Inject

class CartFragment : BaseFragment<CartViewModel>(), View.OnClickListener,
    CartAdapter.OnItemOrderListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: CartViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(CartViewModel::class.java)
    }

    override fun providerViewModel(): CartViewModel = viewModel

    private var adapterCart: CartAdapter? = null

    lateinit var listOrder: ArrayList<Order>
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
        imgCartBack?.setOnClickListener(this)
        btnCartPayment?.setOnClickListener(this)
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentShopping)
        }
    }

    private fun setupRecycleView(arrOrder : ArrayList<Order>) {
        adapterCart = CartAdapter(context, arrOrder, this)
        rcvCartOrder?.layoutManager = LinearLayoutManager(context)
        rcvCartOrder?.adapter = adapterCart
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.getListOrder()
        viewModel.listOrder.observe(viewLifecycleOwner, Observer {
            listOrder = it
            setupRecycleView(listOrder)
        })
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
        listOrder.forEach {
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

    override fun onPlus(position: Int) {
        var count  = listOrder[position].amount
        if (count < 99) {
            count += 1
            listOrder[position].amount = count
            listOrder[position].total = listOrder[position].iceCream.price!! * count
            insertTotal()
            adapterCart?.notifyDataSetChanged()
        }
    }

    override fun onMinus(position: Int) {
        var count  = listOrder[position].amount
        if (count > 1) {
            count -= 1
            listOrder[position].amount = count
            listOrder[position].total = listOrder[position].iceCream.price!! * count
            insertTotal()
            adapterCart?.notifyDataSetChanged()
        } else {
            showDialogWarning("Are you sure?"
                ,"Delete this file"
                ,"Yes,delete it!"
                ,object : DialogClickListener{
                    override fun onConfirmClickListener() {
                        viewModel.removeOrder(position)
                        if (listOrder.size == 0) {
                            activity?.onBackPressed()
                        }else{
                            adapterCart?.notifyDataSetChanged()
                        }
                    }
                    override fun onCancelListener() {
                    }
                })
        }
    }

    override fun insertTotal() {
        val listPrice = ArrayList<Int>()
        for (i in 0 until listOrder.size) {
            listPrice.add(listOrder[i].total)
        }
        showTotalOnView(listPrice)
    }

    private fun showTotalOnView(listPrice: ArrayList<Int>) {
        var total = 0
        for (i in 0 until listPrice.size) {
            total += listPrice[i]
        }
        txtCartTotalPayment.text = total.toString()
    }

    companion object {
        var TAG = CartFragment::class.java.name
    }
}