package com.example.vinid_icecreams.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.activity.HomeActivity
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
        observeData()
        initView(view)
    }

    private fun observeData() {
        mViewModel.mIsRequestRegister.observe(this, Observer {
            if (it){
                registerSuccess()
            }else{
                registerFailse()
            }
        } )
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
                    ProgressLoading.show(context)
                    mViewModel.handleRegister(edtPhoneNumber?.text.toString(),edtPassword?.text.toString(),edtPasswordRepeat?.text.toString())
                }
            }
        }
    }

    private fun registerSuccess() {
        ProgressLoading.dismiss()
        var message = ""
        mViewModel.mToken.observe(this, Observer {
            CommonUtils.instace.savePrefContent(context, CommonUtils.TOKEN,it)
        })
        mViewModel.mMessageSuccess.observe(this, Observer {
           message = it
        })
        KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Register success")
            .setContentText(message)
            .show()
        Handler().postDelayed({
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        },1000)

    }

    private fun registerFailse(){
        ProgressLoading.dismiss()
        var message = ""
        mViewModel.mMessageFailse.observe(this, Observer {
            message = it
        })
        KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Register failse")
            .setContentText(message)
            .show()
    }
}