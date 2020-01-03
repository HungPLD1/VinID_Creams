package com.example.vinid_icecreams.view.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.mock.MockData
import com.example.vinid_icecreams.model.User
import com.example.vinid_icecreams.view.activity.HomeActivity
import de.hdodenhof.circleimageview.CircleImageView

class FragmentProfile : Fragment(),View.OnClickListener {
    private var mImgAvatar : CircleImageView? = null
    private var mTxrName : TextView? = null
    private var mImgBackGround : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = layoutInflater.inflate(R.layout.fragment_profile,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mImgAvatar = view.findViewById(R.id.imgAvatar)
        mTxrName = view.findViewById(R.id.txtNameUser)
        mImgBackGround = view.findViewById(R.id.backgroundUser)


        /*setup view*/
        setupView()
        mImgAvatar?.setOnClickListener(this)
        mTxrName?.setOnClickListener(this)
        mImgBackGround?.setOnClickListener(this)
    }

    private fun setupView() {
        val mUser = MockData.getUser()

        mTxrName?.text = mUser.fullName
        mImgAvatar?.setImageResource(R.drawable.meo)

    }


    override fun onClick(v: View?) {

    }

    override fun onResume() {
        super.onResume()
        (activity as HomeActivity).supportActionBar?.title = resources.getString(R.string.user)
    }
}