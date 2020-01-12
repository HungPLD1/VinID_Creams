package com.example.vinid_icecreams.view.fragment.home.pay

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.connection.body.Bill
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.fragment.home.shopping.FragmentShopping
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import kotlinx.android.synthetic.main.dialog_pay.*
import org.angmarch.views.NiceSpinner
import java.text.DecimalFormat

class FragmentPay : DialogFragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var mShip = 0.0
    /*
    0 : Giao hàng nhận tiền
    1 : Point
    */
    private var mStatus = 0
    private var mStoreSelected = CommonUtils.instace.getStoreSelected()
    private var mBill: Bill? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }


    private var txtChargeOrder: TextView? = null
    private var txtChargeShip: TextView? = null
    private var txtChargeTotal: TextView? = null
    private var spnPayment: NiceSpinner? = null
    private var btnPayment: Button? = null
    private var btnClose: ImageView? = null

    companion object {
        val TAG = FragmentPay::class.java.name
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_pay, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        getData()
        setChareShip()
        showData()
        observeData()
    }

    private fun observeData() {
        mViewModel.mIsPayment.observe(this, Observer {
            if (it) {
                paymentSuccess()
                ProgressLoading.dismiss()
            } else {
                paymentFailse()
                ProgressLoading.dismiss()
            }
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
        txtChargeShip?.text = String.format(newFormat.format(mShip)) + " $"
        txtChargeOrder?.text = CommonUtils.instace.getTotalPayment().toString() + " $"
        txtChargeTotal?.text =
            String.format(newFormat.format(CommonUtils.instace.getTotalPayment() + mShip).toString()) + " $"
        txt_Store_Location?.text = mStoreSelected?.name
    }

    private fun initView(view: View) {
        txtChargeOrder = view.findViewById(R.id.txt_charge_order)
        txtChargeShip = view.findViewById(R.id.txt_charge_ship)
        txtChargeTotal = view.findViewById(R.id.txt_total_charge)
        spnPayment = view.findViewById(R.id.spn_payments)
        btnPayment = view.findViewById(R.id.btn_goto_payment)
        btnClose = view.findViewById(R.id.imgClose)

        prepareSpinner()
        /*even click*/
        btnClose?.setOnClickListener(this)
        btnPayment?.setOnClickListener(this)
    }

    private fun prepareSpinner() {
        val mListType = listOf("Giao hàng nhận tiền", "Point")
        spnPayment?.attachDataSource(mListType)
        spnPayment?.setOnClickListener(this)
    }


    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgClose -> {
                    dismiss()
                }
                R.id.btn_goto_payment -> handlePayment()
            }
        }
    }

    private fun handlePayment(){
        addDataToBill()
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            Log.d(TAG, mBill.toString())
            mViewModel.handlePayment(mBill!!)
        } else {
            showNoConnection()
        }
    }

    private fun addDataToBill() {
        mBill?.status = mStatus
        mBill?.shipFee = mShip.toInt()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        mStatus = 0
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (position) {
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
        var message = ""
        mViewModel.mMessageFailse.observe(this, Observer {
            message = it
        })
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText("Payment Failse")
            .setContentText(message)
            .show()
    }

    private fun paymentSuccess() {
        var message = ""
        mViewModel.mMessageSuccess.observe(this, Observer {
            message = it
        })
        var dialogPaySuccess = KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Payment success")
            .setContentText(message).setConfirmClickListener {
                dialog?.dismiss()
                it.dismiss()
                CommonUtils.mListOrder?.clear()
                val fragmentShopping = FragmentShopping()
                fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,fragmentShopping)?.addToBackStack(null)?.commit()
            }
        dialogPaySuccess.setCanceledOnTouchOutside(false)
        dialogPaySuccess.show()

    }
}