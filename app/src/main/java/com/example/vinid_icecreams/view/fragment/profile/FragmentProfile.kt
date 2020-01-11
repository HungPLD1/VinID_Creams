package com.example.vinid_icecreams.view.fragment.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.mock.MockData
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.view.activity.HomeActivity
import com.example.vinid_icecreams.view.activity.LoginActivity
import de.hdodenhof.circleimageview.CircleImageView
import com.developer.kalert.KAlertDialog


class FragmentProfile : Fragment(), View.OnClickListener {
    private var mImgAvatar: CircleImageView? = null
    private var mTxrName: TextView? = null
    private var mImgBackGround: ImageView? = null
    private var mBtnLogout: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mImgAvatar = view.findViewById(R.id.imgAvatar)
        mTxrName = view.findViewById(R.id.txtNameUser)
        mImgBackGround = view.findViewById(R.id.backgroundUser)
        mBtnLogout = view.findViewById(R.id.profile_logout)


        /*setup view*/
        setupView()
        mImgAvatar?.setOnClickListener(this)
        mTxrName?.setOnClickListener(this)
        mImgBackGround?.setOnClickListener(this)

        mBtnLogout?.setOnClickListener(this)

    }

    private fun setupView() {
        val mUser = MockData.getUser()

        mTxrName?.text = mUser.fullName
        mImgAvatar?.setImageResource(R.drawable.meo)

    }


    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.profile_logout -> {
                    var dialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE)
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
            }
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).supportActionBar?.title = resources.getString(R.string.user)
    }
}