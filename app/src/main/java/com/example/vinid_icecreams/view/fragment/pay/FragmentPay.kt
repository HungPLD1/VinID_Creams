package com.example.vinid_icecreams.view.fragment.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.view.fragment.cart.FragmentCart

class FragmentPay : Fragment(),View.OnClickListener {


    private var txtChargeOrder : TextView? = null
    private var txtChargeShip : TextView? = null
    private var txtChargeTotal : TextView? = null
    private var spnPayment : Spinner? = null
    private var btnPayment : Button? = null
    private var btnBack : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = inflater.inflate(R.layout.fragment_pay,container,false)
        initView(view)
        return view
    }

    private fun initView(view: View?) {
        txtChargeOrder = view?.findViewById(R.id.txt_charge_order)
        txtChargeShip= view?.findViewById(R.id.txt_charge_ship)
        txtChargeTotal= view?.findViewById(R.id.txt_total_charge)
        spnPayment= view?.findViewById(R.id.spnPayments)
        btnPayment= view?.findViewById(R.id.btn_payment)
        btnBack = view?.findViewById(R.id.imgBack_To_Cart)
    }

    override fun onClick(view: View?) {
       if (view != null){
           when(view.id){
               R.id.imgBack_To_Cart ->{
                   val fragmentCart  = FragmentCart()
                   fragmentManager?.beginTransaction()?.replace(R.id.containerHome,fragmentCart)?.addToBackStack(null)?.commit()
               }
           }
       }
    }
}