package com.example.vinid_icecreams.ui.fragment.user.wallet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.User
import kotlinx.android.synthetic.main.fragment_wallet.*
import javax.inject.Inject

class WalletFragment : BaseFragment<WalletViewModel>(),View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: WalletViewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(WalletViewModel::class.java)
    }

    override fun providerViewModel(): WalletViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_wallet,container,false)
    }

    override fun setUpUI() {
        super.setUpUI()
        handleOnClick()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.user.observe(viewLifecycleOwner, Observer { userInfo ->
            setupMoney(userInfo)
        })

        viewModel.isChargePoint.observe(viewLifecycleOwner, Observer {
            if (it){
                showDialogSuccess("Charge success","Nạp tiền thành công")
            }else{
                showDiaLogFailed("Charge Failed","Nạp tiền thất bại")
            }
        })

        viewModel.getUserProfile()
    }


    private fun handleOnClick() {
        btnShowInputMoney.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
        imgBack.setOnClickListener(this)
    }


    override fun onClick(p0: View?) {
        if (p0 != null){
            when(p0){
                btnShowInputMoney ->{
                    if (viewWalletSubmit.isVisible ){
                        viewWalletSubmit.visibility = View.GONE
                    }else{
                        viewWalletSubmit.visibility = View.VISIBLE
                    }
                }
                btnSubmit -> handleRechargePoint()
                imgBack -> findNavController().navigate(R.id.fragmentProfile)
            }
        }
    }

    private fun handleRechargePoint() {
        if(isConnectToNetwork(context)) {
            val amount  = edtInputMoney?.text?.toString()
            if (!amount.isNullOrEmpty()){
                viewModel.setPointUser(Integer.parseInt(amount))
            }else{
                showDiaLogFailed("Charge Failed", "Số tiền không được để trống")
            }
        }else{
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupMoney(user : User?){
        txtWalletMoney.text = user?.vinIdPoint.toString()+ " $"
        edtInputMoney?.text?.clear()
        viewWalletSubmit?.visibility = View.GONE
    }
}