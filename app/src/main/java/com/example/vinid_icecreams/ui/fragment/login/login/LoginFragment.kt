package com.example.vinid_icecreams.ui.fragment.login.login

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
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject


class LoginFragment : BaseFragment<LoginViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }

    override fun providerViewModel(): LoginViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun setUpUI() {
        btnLoginGo.setOnClickListener(this)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.isLoginSuccess.observe(viewLifecycleOwner, Observer {
            if (it) {
                loginSuccess()
            } else {
                loginFailed()
            }
        })
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view) {
                btnLoginGo -> handleLogin()
            }
        }
    }

    private fun handleLogin() {
        if (isConnectToNetwork(context)) {
            viewModel.handleLogin(edtPhoneNumber?.text.toString(), edtPassword?.text.toString())
        } else {
            showNoConnection()
        }
    }


    /*login success*/
    private fun loginSuccess() {
        showDialogSuccess(LOGIN_SUCCESS,messageSuccess)
        Handler().postDelayed({
            startActivity(Intent(activity,
                HomeActivity::class.java))
            activity?.finish()
        },1000)
    }

    /*login failed*/
    private fun loginFailed(){
        showDiaLogFailed(LOGIN_FAILED,messageFailed)
    }

    companion object {
        var TAG = LoginFragment::class.java.name
        const val LOGIN_SUCCESS =  "Đăng nhập thành công"
        const val LOGIN_FAILED = "Đăng nhập thất bại"
    }
}