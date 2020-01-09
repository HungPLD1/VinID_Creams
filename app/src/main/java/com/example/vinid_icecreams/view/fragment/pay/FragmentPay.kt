package com.example.vinid_icecreams.view.fragment.pay

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import kotlinx.android.synthetic.main.dialog_pay.*
import java.text.DecimalFormat

class FragmentPay : DialogFragment(), View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var mShip = 0.0
    /*
    0 : Giao hàng nhận tiền
    1 : Point
    */
    private var payment = 0
    private var mStoreSelected = CommonUtils.instace.getStoreSelected()


    private var txtChargeOrder: TextView? = null
    private var txtChargeShip: TextView? = null
    private var txtChargeTotal: TextView? = null
    private var spnPayment: Spinner? = null
    private var btnPayment: Button? = null
    private var btnClose: ImageView? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_pay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        setChareShip()
        showData()
    }

    @SuppressLint("SetTextI18n")
    private fun showData() {
        val newFormat = DecimalFormat("###.#")
        txtChargeShip?.text = String.format(newFormat.format(mShip)) + " $"
        txtChargeOrder?.text = CommonUtils.instace.getTotalPayment().toString()
        txtChargeTotal?.text =  String.format(newFormat.format(CommonUtils.instace.getTotalPayment() + mShip).toString())+ " $"
        txt_Store_Location?.text = mStoreSelected?.name
    }

    private fun initView(view: View) {
        txtChargeOrder = view.findViewById(R.id.txt_charge_order)
        txtChargeShip = view.findViewById(R.id.txt_charge_ship)
        txtChargeTotal = view.findViewById(R.id.txt_total_charge)
        spnPayment = view.findViewById(R.id.spn_payments)
        btnPayment = view.findViewById(R.id.btn_payment)
        btnClose = view.findViewById(R.id.imgClose)

        setChareShip()
        prepareSpinner()
        /*even click*/
        btnClose?.setOnClickListener(this)
        btnPayment?.setOnClickListener(this)
    }

    private fun prepareSpinner() {
        val mListType = arrayOf("Giao hàng nhận tiền", "Point")
        val mAdapterType = context?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_dropdown_item, // Layout
                mListType // Array
            )
        }
        spnPayment?.adapter = mAdapterType
        spnPayment?.setSelection(0)
        spnPayment?.onItemSelectedListener = this
    }


    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgClose -> {
                    dismiss()
                }
                R.id.btn_payment -> {

                }
            }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        payment = 0
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        when (position) {
            0 -> payment = 0
            1 -> payment = 1
        }
    }

    private fun setChareShip() {
        /*phí ship 2$/1 km */
        if (mStoreSelected?.range != null) {
            mShip = mStoreSelected!!.range * 2
        }
    }


}