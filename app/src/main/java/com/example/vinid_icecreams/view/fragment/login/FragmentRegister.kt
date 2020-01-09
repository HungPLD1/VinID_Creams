package com.example.vinid_icecreams.view.fragment.login

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentRegister : Fragment(),View.OnClickListener {
    var TAG = FragmentRegister::class.java.name

    private var edtPhoneNumber : EditText? = null
    private var edtPassword : EditText? = null
    private var edtPasswordRepeat : EditText? = null
    private var btnRegister :Button? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    private fun initView(view: View) {
        edtPhoneNumber = view.findViewById(R.id.edt_register_phone_number)
        edtPassword = view.findViewById(R.id.edt_register_password)
        edtPasswordRepeat = view.findViewById(R.id.edt_register_repeat_password)
        btnRegister = view.findViewById(R.id.btn_register)

        btnRegister?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!= null){
            when(v.id){
                R.id.btn_register -> {
                    handleRegister()
                }
            }
        }
    }

    private fun handleRegister() {
        if (handleVerifyPhoneNumber() && handleComparedPassword()){
            val phoneNumber = Integer.parseInt(edtPhoneNumber?.text.toString())
            mViewModel.handleRegister(phoneNumber, edtPassword?.text.toString())
        }else{
            registerFailse()
        }
    }

    private fun handleComparedPassword(): Boolean{
        if (edtPassword?.text.isNullOrEmpty() || edtPasswordRepeat?.text.isNullOrEmpty()){
            return false
        }
        if (edtPassword?.text?.length!! < 8 || edtPasswordRepeat?.text?.length!! < 8 ){
            return false
        }
        return edtPassword?.text?.toString()?.equals(edtPasswordRepeat?.text.toString())!!
    }

    private fun handleVerifyPhoneNumber ():Boolean{
        if(edtPhoneNumber?.text.isNullOrEmpty()){
            return false
        }
        return !(edtPhoneNumber?.text?.length != 9 && edtPhoneNumber?.text?.length != 10)
    }


    private fun registerFailse(){
        CommonUtils.instace.showSomeThingWentWrong(activity)
    }

    private fun registerSuccess(){

    }
}