package com.example.vinid_icecreams.ui.fragment.login

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
import com.example.vinid_icecreams.ui.activity.home.HomeActivity
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream


class FragmentLogin : Fragment() ,View.OnClickListener {

    private var edtPhoneNumber : EditText? = null
    private var edtPassword : EditText? = null
    private var mMessageSuccess : String? = null
    private var mMessageFailse : String? = null

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
        mViewModel.mIsRequestLogin.observe(viewLifecycleOwner, Observer {
            if (it){
                loginSuccess()
            }else{
                loginFailse()
            }
        } )
        mViewModel.mToken.observe(viewLifecycleOwner, Observer {
            CommonUtils.token = it
            CommonUtils.instace.savePrefContent(context,CommonUtils.TOKEN,it)
        })
        mViewModel.mMessageSuccess.observe(viewLifecycleOwner, Observer {
            mMessageSuccess = it
        })
        mViewModel.mMessageFail.observe(viewLifecycleOwner, Observer {
            mMessageFailse = it
        })
    }

    private fun initView(view: View?) {
        btnLogin = view?.findViewById(R.id.btnLoginGo)
        edtPhoneNumber = view?.findViewById(R.id.edtLoginPhoneNumber)
        edtPassword = view?.findViewById(R.id.edtLoginPassword)

        btnLogin?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.btnLoginGo -> handleLogin()
            }
        }
    }

    private fun handleLogin(){
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.handleLogin(edtPhoneNumber?.text.toString(), edtPassword?.text.toString())
        }else{
            showNoConnection()
        }
    }


    /*login success*/
    private fun loginSuccess() {
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Login success")
            .setContentText(mMessageSuccess)
            .show()
        Handler().postDelayed({
            startActivity(Intent(activity,
                HomeActivity::class.java))
            activity?.finish()
        },1000)
    }

    /*login failse*/
    private fun loginFailse(){
        ProgressLoading.dismiss()
        KAlertDialog(context, KAlertDialog.ERROR_TYPE)
            .setTitleText("Login error")
            .setContentText(mMessageFailse)
            .show()
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                handleLogin()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}