package com.example.vinid_icecreams.ui.fragment.user

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.ui.activity.login.LoginActivity
import de.hdodenhof.circleimageview.CircleImageView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream


class HomeUser : Fragment(), View.OnClickListener {
    private var mUser : User? = null

    private var mImgAvatar: CircleImageView? = null
    private var mTxrName: TextView? = null
    private var mImgBackGround: ImageView? = null
    private var mTxtWallet :TextView? = null
    private var mBtnLogout: CardView? = null
    private var mBtnHistory : CardView?= null
    private var mBtnWallet : CardView? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    companion object{
        const val DEFAULT_NAME = "Default name"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeData()
        handleGetUserInfo()
    }

    private fun initView(view: View) {
        mImgAvatar = view.findViewById(R.id.imgAvatar)
        mTxrName = view.findViewById(R.id.txtNameUser)
        mImgBackGround = view.findViewById(R.id.backgroundUser)
        mBtnLogout = view.findViewById(R.id.profile_logout)
        mBtnHistory = view.findViewById(R.id.profile_history)
        mTxtWallet = view.findViewById(R.id.txt_profile_wallet)
        mBtnWallet = view.findViewById(R.id.profile_wallet)
        /*setup view*/
        mImgAvatar?.setOnClickListener(this)
        mTxrName?.setOnClickListener(this)
        mImgBackGround?.setOnClickListener(this)

        mBtnLogout?.setOnClickListener(this)
        mBtnHistory?.setOnClickListener(this)
        mBtnWallet?.setOnClickListener(this)
    }


    /*observe data*/
    private fun observeData() {
            mViewModel.mUser.observe(viewLifecycleOwner, Observer { data ->
                ProgressLoading.dismiss()
                mUser = data
                CommonUtils.mAmount = mUser?.vinIdPoint!!
                setupView()
            })
    }

    private fun handleGetUserInfo(){
        if(CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getUserProfile()
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        if (mUser?.fullName.isNullOrEmpty()){
            mTxrName?.text = DEFAULT_NAME
        }else {
            mTxrName?.text = mUser?.fullName
        }
        mImgAvatar?.setImageResource(R.drawable.meo)
        mTxtWallet?.text = mUser?.vinIdPoint.toString() + " $"
    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.profile_logout -> {
                    val dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                        .setTitleText("Logout")
                        .setContentText("Bạn có muốn đăng xuất ?")
                        .setConfirmText("Đúng rồi ")
                        .setConfirmClickListener {
                            it.dismiss()
                            context?.getSharedPreferences(
                                CommonUtils.PREF_SAVE_NAME,
                                Context.MODE_PRIVATE
                            )?.edit()?.remove(CommonUtils.TOKEN)?.apply()
                            startActivity(Intent(activity, LoginActivity::class.java))
                            activity?.finish()
                        }
                    dialog.setCanceledOnTouchOutside(true)
                    dialog.show()
                }
                R.id.profile_history ->{
                    findNavController().navigate(R.id.fragmentOrderHistory)
                }
                R.id.profile_wallet ->{
                    findNavController().navigate(R.id.fragmentWallet)
                }
            }
        }
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                handleGetUserInfo()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}