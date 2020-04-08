package com.example.vinid_icecreams.ui.fragment.home.pay

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseDialogFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.remote.requestBody.BillRequest
import kotlinx.android.synthetic.main.dialog_pay.*
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import java.text.DecimalFormat
import javax.inject.Inject

class PayFragment : BaseDialogFragment<PayViewModel>(), View.OnClickListener,
    OnSpinnerItemSelectedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: PayViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(PayViewModel::class.java)
    }

    override fun providerViewModel(): PayViewModel = viewModel

    private var shipFee = 0.0
    private var status = 0
    private var storeSelected: Store? = null
    private var billRequest: BillRequest? = null

    companion object {
        val TAG = PayFragment::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_pay, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        initView()
        setupChargeShip()
        setupBill()
        setupSpinner()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.isPayment.observe(viewLifecycleOwner, Observer {
            if (it) {
                paymentSuccess()
            } else {
                showDiaLogFailed("Payment Failed", messageFailed)
            }
        })
    }

    private fun paymentSuccess() {
        val dialogPaySuccess = KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Payment success")
            .setContentText(messageSuccess).setConfirmClickListener {
                dialog?.dismiss()
                it.dismiss()
                viewModel.clearOrder()
                findNavController().navigate(R.id.fragmentShopping)
            }
        dialogPaySuccess.setCanceledOnTouchOutside(false)
        dialogPaySuccess.show()
    }

    private fun getDataBill() {
        val bundle = arguments
        val data = bundle?.getSerializable("BILL")
        if (data != null) {
            billRequest = data as BillRequest
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupBill() {
        getDataBill()
        val newFormat = DecimalFormat("###.#")
        txtPaymentChargeShip?.text = String.format(newFormat.format(shipFee)) + " $"
        txtPaymentChargeOrder?.text = viewModel.getTotalPayment().toString() + " $"
        txtPaymentTotalCharge?.text =
            String.format(newFormat.format(viewModel.getTotalPayment()!! + shipFee).toString()) + " $"
        txtPaymentStoreName?.text = storeSelected?.name
    }

    private fun initView() {
        storeSelected = viewModel.getStoreSelected()
        /*even click*/
        imgPaymentClose?.setOnClickListener(this)
        btnPaymentAccept?.setOnClickListener(this)
    }

    private fun setupSpinner() {
        val mListType = listOf("Giao hàng nhận tiền", "Point")
        spnPayment?.attachDataSource(mListType)
        spnPayment?.onSpinnerItemSelectedListener = this
    }


    override fun onClick(view: View?) {
        if (view != null) {
            when (view) {
                imgPaymentClose -> {
                    dismiss()
                }
                btnPaymentAccept -> handlePayment()
            }
        }
    }

    private fun handlePayment() {
        addDataToBill()
        if (isConnectToNetwork(context)) {
            viewModel.handlePayment(billRequest!!)
        } else {
            showNoConnection()
        }
    }

    private fun addDataToBill() {
        billRequest?.status = status
        billRequest?.shipFee = shipFee.toInt()
    }

    override fun onItemSelected(parent: NiceSpinner?, view: View?, position: Int, id: Long) {
        when (position) {
            0 -> status = 0
            1 -> status = 1
        }
    }

    private fun setupChargeShip() {
        /*phí ship 10$/1 km */
        if (storeSelected?.range != null) {
            shipFee = storeSelected!!.range * 10
        }
    }
}