package com.example.vinid_icecreams.view.fragment.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.vinid_icecreams.R

class FragmentPay : DialogFragment(),View.OnClickListener {


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

        /*even click*/
        btnClose?.setOnClickListener(this)

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
}