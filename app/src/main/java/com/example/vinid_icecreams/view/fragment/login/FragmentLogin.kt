package com.example.vinid_icecreams.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
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


class FragmentLogin : Fragment() ,View.OnClickListener {

    private var edtPhoneNumber : EditText? = null
    private var edtPassword : EditText? = null

    private var btnLogin : Button? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    companion object{
        var TAG = FragmentLogin::class.java.name
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeData()
    }

    /*observe data*/
    private fun observeData() {
        mViewModel.mIsRequestLogin.observe(this, Observer {
            if (it){
                loginSuccess()
            }else{
                loginFailse()
            }
        } )
    }

    private fun initView(view: View?) {
        btnLogin = view?.findViewById(R.id.btn_go)
        edtPhoneNumber = view?.findViewById(R.id.edt_phone_number)
        edtPassword = view?.findViewById(R.id.edt_password)

        btnLogin?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.btn_go -> {
                    ProgressLoading.show(context)
                    mViewModel.handleLogin(edtPhoneNumber?.text.toString(),edtPassword?.text.toString())}
            }
        }
    }


    /*login success*/
    private fun loginSuccess() {
        ProgressLoading.dismiss()
        var message = ""
        mViewModel.mToken.observe(this, Observer {
            CommonUtils.instace.savePrefContent(context,CommonUtils.TOKEN,it)
            Log.d(TAG,it.toString())
        })
        mViewModel.mMessageSuccess.observe(this, Observer {
           message = it
        })
        KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Login success")
            .setContentText(message)
            .show()
        Handler().postDelayed({
            startActivity(Intent(activity,HomeActivity::class.java))
            activity?.finish()
        },1000)
    }

    /*login failse*/
    private fun loginFailse(){
        ProgressLoading.dismiss()
        var message = ""
        mViewModel.mMessageFailse.observe(this, Observer {
            message = it
        })
        KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Login error")
            .setContentText(message)
            .show()
    }



}