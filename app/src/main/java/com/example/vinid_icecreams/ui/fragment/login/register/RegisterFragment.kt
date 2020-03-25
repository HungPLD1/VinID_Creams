package com.example.vinid_icecreams.ui.fragment.login.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.ui.activity.home.HomeActivity
import kotlinx.android.synthetic.main.fragment_register.*
import javax.inject.Inject

class RegisterFragment : BaseFragment<RegisterViewModel>(),View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RegisterViewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(RegisterViewModel::class.java)
    }

    override fun providerViewModel(): RegisterViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun setUpUI() {
        btnRegister.setOnClickListener(this)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        handleRegister()
        viewModel.isRegisterSuccess.observe(viewLifecycleOwner, Observer {
            if (it){
                registerSuccess()
            }else{
                registerFail()
            }
        } )
    }

    override fun onClick(v: View?) {
        if (v!= null){
            when(v){
                btnRegister -> handleRegister()
            }
        } 
    }

    private fun handleRegister() {
        if (isConnectToNetwork(context)) {
            viewModel.handleRegister(
                edtRegisterPhoneNumber?.text.toString(),
                edtRegisterPassword?.text.toString(),
                edtRegisterRepeatPassword?.text.toString()
            )
        }else{
            showNoConnection()
        }
    }

    private fun registerSuccess() {
        showDialogSuccess(REGISTER_SUCCESS,messageSuccess)
        Handler().postDelayed({
            startActivity(Intent(activity, HomeActivity::class.java))
            activity?.finish()
        },1000)

    }

    private fun registerFail(){
      showDiaLogFailed(REGISTER_FAILED,messageFailed)
    }

    companion object{
        var TAG = RegisterFragment::class.java.name
        var REGISTER_SUCCESS = "Đăng kí thành công"
        var REGISTER_FAILED = "Đăng kí thất bại"
    }

}