package com.example.vinid_icecreams.ui.fragment.home.pay

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.repository.remote.requestBody.Bill
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import kotlinx.android.synthetic.main.dialog_pay.*
import org.angmarch.views.NiceSpinner
import org.angmarch.views.OnSpinnerItemSelectedListener
import java.text.DecimalFormat

class FragmentPay : DialogFragment(), View.OnClickListener, OnSpinnerItemSelectedListener {
    private var mMessageFail : String? = null
    private var mMessageSuccess : String? = null

    private var mShip = 0.0
    private var mStatus = 0
    private var mStoreSelected:Store? = null
    private var mBill: Bill? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    companion object {
        val TAG = FragmentPay::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return inflater.inflate(R.layout.dialog_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        getData()
        setChareShip()
        showData()
        observeData()
    }


    private fun observeData() {
        mViewModel.mIsPayment.observe(viewLifecycleOwner, Observer {
            if (it) {
                paymentSuccess()
                ProgressLoading.dismiss()
            } else {
                paymentFailse()
                ProgressLoading.dismiss()
            }
        })

        mViewModel.mMessageSuccess.observe(viewLifecycleOwner, Observer {
            mMessageSuccess = it
        })

        mViewModel.mMessageFail.observe(viewLifecycleOwner, Observer {
            mMessageFail = it
        })
    }



    private fun getData() {
        val bundle = arguments
        val mData = bundle?.getSerializable("BILL")
        if (mData != null) {
            mBill = mData as Bill
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showData() {
        val newFormat = DecimalFormat("###.#")
        txtPaymentChargeShip?.text = String.format(newFormat.format(mShip)) + " $"
        txtPaymentChargeOrder?.text = CommonUtils.instace.getTotalPayment().toString() + " $"
        txtPaymentTotalCharge?.text =
            String.format(newFormat.format(CommonUtils.instace.getTotalPayment() + mShip).toString()) + " $"
        txtPaymentStoreName?.text = mStoreSelected?.name
    }

    private fun initView() {
        prepareSpinner()
        /*even click*/
        imgPaymentClose?.setOnClickListener(this)
        btnPaymentAccept?.setOnClickListener(this)
    }

    private fun prepareSpinner() {
        val mListType = listOf("Giao hàng nhận tiền", "Point")
        spnPayment?.attachDataSource(mListType)
        spnPayment?.onSpinnerItemSelectedListener = this
    }


    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgPaymentClose -> {
                    dismiss()
                }
                R.id.btnCartPayment -> handlePayment()
            }
        }
    }

    private fun handlePayment(){
        addDataToBill()
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.handlePayment(mBill!!)
        } else {
            showNoConnection()
        }
    }

    private fun addDataToBill() {
        mBill?.status = mStatus
        mBill?.shipFee = mShip.toInt()
    }

    override fun onItemSelected(parent: NiceSpinner?, view: View?, position: Int, id: Long) {
        when(position){
            0 -> mStatus = 0
            1 -> mStatus = 1
        }
    }

    private fun setChareShip() {
        /*phí ship 2$/1 km */
        if (mStoreSelected?.range != null) {
            mShip = mStoreSelected!!.range * 2
        }
    }


    private fun showNoConnection() {
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener {
                it.dismiss()
                handlePayment()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }


    private fun paymentFailse() {
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText("Payment Failse")
            .setContentText(mMessageFail)
            .show()
    }

    private fun paymentSuccess() {
        val dialogPaySuccess = KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Payment success")
            .setContentText(mMessageSuccess).setConfirmClickListener {
                dialog?.dismiss()
                it.dismiss()
                findNavController().navigate(R.id.fragmentShopping)
            }
        dialogPaySuccess.setCanceledOnTouchOutside(false)
        dialogPaySuccess.show()

    }
}