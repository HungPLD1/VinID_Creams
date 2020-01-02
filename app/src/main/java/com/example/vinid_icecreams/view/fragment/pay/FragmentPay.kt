package com.example.vinid_icecreams.view.fragment.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils

class FragmentPay : DialogFragment(),View.OnClickListener, AdapterView.OnItemSelectedListener {
    private var mShip = 0


    private var txtChargeOrder : TextView? = null
    private var txtChargeShip : TextView? = null
    private var txtChargeTotal : TextView? = null
    private var spnPayment : Spinner? = null
    private var btnPayment : Button? = null
    private var btnClose : ImageView? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pay,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtChargeOrder = view.findViewById(R.id.txt_charge_order)
        txtChargeShip= view.findViewById(R.id.txt_charge_ship)
        txtChargeTotal= view.findViewById(R.id.txt_total_charge)
        spnPayment= view.findViewById(R.id.spnPayments)
        btnPayment= view.findViewById(R.id.btn_payment)
        btnClose = view.findViewById(R.id.imgClose)

        prepareView()
        /*even click*/
        btnClose?.setOnClickListener(this)

    }

    private fun prepareView() {
        txtChargeShip?.text = mShip.toString()
        txtChargeOrder?.text = CommonUtils.instace.getTotalPayment().toString()
        txtChargeTotal?.text = (CommonUtils.instace.getTotalPayment() + mShip).toString()
        prepareSpinner()
    }

    private fun prepareSpinner() {
            val mListType = arrayOf("Giao hàng nhận tiền","Point")
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
       if (view != null){
           when(view.id){
                R.id.imgClose ->{
                    dismiss()
                }
           }
       }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }
}