package com.example.vinid_icecreams.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.activity.HomeActivity
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import kotlinx.android.synthetic.main.fragment_register.*

class FragmentRegister : Fragment(),View.OnClickListener {
    var TAG = FragmentRegister::class.java.name

    private var mMessageSuccess : String? = null
    private var mMessageFail : String? = null
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
        mViewModel.mIsRequestRegister.observe(viewLifecycleOwner, Observer {
            if (it){
                registerSuccess()
            }else{
                registerFail()
            }
        } )
        mViewModel.mToken.observe(viewLifecycleOwner, Observer {
            CommonUtils.token = it
            CommonUtils.instace.savePrefContent(context,CommonUtils.TOKEN,it)
        })
        mViewModel.mMessageSuccess.observe(viewLifecycleOwner, Observer {
            mMessageSuccess = it
        })
        mViewModel.mMessageFailse.observe(viewLifecycleOwner, Observer {
            mMessageFail = it
        })
    }

    private fun initView(view: View) {
        btnRegister?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v!= null){
            when(v.id){
                R.id.btnRegister -> handleRegister()
            }
        } 
    }

    private fun handleRegister() {
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.handleRegister(
                edtRegisterPhoneNumber?.text.toString(),
                edtRegisterPassword?.text.toString(),
                edtRegisterRepeatPassword?.text.toString()
            )
        }else{
            showNoConnection()
        }
    }

    private fun registerSuccess() {
        ProgressLoading.dismiss()
        KAlertDialog(activity, KAlertDialog.SUCCESS_TYPE)
            .setTitleText("Register success")
            .setContentText(mMessageSuccess)
            .show()
        Handler().postDelayed({
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        },1000)

    }

    private fun registerFail(){
        ProgressLoading.dismiss()
        KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Register failse")
            .setContentText(mMessageFail)
            .show()
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                handleRegister()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}