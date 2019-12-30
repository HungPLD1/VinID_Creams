package com.example.vinid_icecreams.view.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import de.hdodenhof.circleimageview.CircleImageView

class FragmentProfile : Fragment(),View.OnClickListener {
    private var mAvatar : CircleImageView? = null
    private var mName : TextView? = null
    private var mBackGround : LinearLayout? = null
    private var isEdit = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = layoutInflater.inflate(R.layout.fragment_profile,container,false)
        initView(view)
        checkIsEdit()
        return view
    }

    private fun checkIsEdit() {
        if (isEdit){
            mAvatar?.isClickable = false
            mName?.isClickable = false
            mBackGround?.isClickable = false
        }else{
            mAvatar?.isClickable = true
            mName?.isClickable = true
            mBackGround?.isClickable = true
        }
    }

    private fun initView(view: View?) {
        mAvatar = view?.findViewById(R.id.imgAvatar)
        mName = view?.findViewById(R.id.txtNameUser)
        mBackGround = view?.findViewById(R.id.backgroundUser)

        mAvatar?.setOnClickListener(this)
        mName?.setOnClickListener(this)
        mBackGround?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

    }
}