package com.example.vinid_icecreams.ui.fragment.user.homeUser

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.ui.activity.login.LoginActivity
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.User
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


class UserFragment : BaseFragment<UserViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: UserViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(UserViewModel::class.java)
    }
    override fun providerViewModel(): UserViewModel = viewModel

    private var user : User? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        handleOnClick()
        getUserInfo()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.user.observe(viewLifecycleOwner, Observer { data ->
            user = data
            setupViewUser()
        })
        getUserInfo()
    }

    private fun handleOnClick() {
        /*setup view*/
        imgAvatar?.setOnClickListener(this)
        txtNameUser?.setOnClickListener(this)
        imgBackground?.setOnClickListener(this)
        btnLogout?.setOnClickListener(this)
        btnHistory?.setOnClickListener(this)
        btnBalance?.setOnClickListener(this)
    }

    private fun getUserInfo(){
        if(isConnectToNetwork(context)) {
            viewModel.getUserProfile()
        }else{
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupViewUser() {
        if (user?.fullName.isNullOrEmpty()){
            txtNameUser.text = DEFAULT_NAME
        }else {
            txtNameUser.text = user?.fullName
        }
        imgAvatar.setImageResource(R.drawable.meo)
        txtBalance.text = user?.vinIdPoint.toString() + " $"
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v) {
                btnLogout -> {
                    val dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Bạn có muốn đăng xuất ?")
                        .setConfirmText("Đúng rồi ")
                        .setConfirmClickListener {
                            it.dismiss()
                            viewModel.removeToken()
                            startActivity(Intent(activity, LoginActivity::class.java))
                            activity?.finish()
                        }
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()
                }
                btnHistory ->{
                    findNavController().navigate(R.id.fragmentOrderHistory)
                }
                btnBalance ->{
                    findNavController().navigate(R.id.fragmentWallet)
                }
            }
        }
    }

    companion object{
        const val DEFAULT_NAME = "Default name"
    }
}