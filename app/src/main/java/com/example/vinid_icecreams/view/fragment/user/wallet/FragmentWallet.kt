package com.example.vinid_icecreams.view.fragment.user.wallet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.activity.HomeActivity
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentWallet : Fragment(),View.OnClickListener {


    private var btnShow : LinearLayout? = null
    private var txtMoney : TextView? = null
    private var btnSubmit :Button? =null
    private var edtInputMoney : EditText? = null
    private var formSubmit : LinearLayout? = null
    private var mUser : User? = null
    private var mBtnBack : ImageView? = null

    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_wallet,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        txtMoney?.text = CommonUtils.mAmount.toString()
    }

    private fun initView(view: View) {
        btnShow = view.findViewById(R.id.btn_wallet_show_input_money)
        txtMoney = view.findViewById(R.id.txt_wallet_money)
        btnSubmit = view.findViewById(R.id.btn_wallet_submit)
        edtInputMoney = view.findViewById(R.id.edt_wallet_number)
        formSubmit = view.findViewById(R.id.ln_wallet_form_submit)
        mBtnBack = view.findViewById(R.id.img_wallet_back)


        btnShow?.setOnClickListener(this)
        btnSubmit?.setOnClickListener(this)
        mBtnBack?.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        if (p0 != null){
            when(p0.id){
                R.id.btn_wallet_show_input_money ->{
                    if (formSubmit!!.isVisible){
                        formSubmit?.visibility = View.GONE
                    }else{
                        formSubmit?.visibility = View.VISIBLE
                    }
                }
                R.id.btn_wallet_submit -> observeData()
                R.id.img_wallet_back -> findNavController().navigate(R.id.fragmentProfile)
            }
        }
    }


    /*observe data*/
    private fun observeData() {
        if(CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            val amount  = edtInputMoney?.text.toString()
            mViewModel.setPointUser(Integer.parseInt(amount))
            mViewModel.mUser.observe(viewLifecycleOwner, Observer { data ->
                ProgressLoading.dismiss()
                mUser = data
                setupView()
            })
        }else{
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView(){
        txtMoney?.text = mUser?.vinIdPoint.toString()+ " $"
        edtInputMoney?.text?.clear()
        formSubmit?.visibility = View.GONE
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                observeData()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }


}