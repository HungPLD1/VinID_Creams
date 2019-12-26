package com.example.vinid_icecreams.view.fragment.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.activity.HomeActivity


class FragmentLogin : Fragment() ,View.OnClickListener {


    var btnLogin : Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login,container,false)
        initView(view)
        return view
    }

    private fun initView(view: View?) {
        btnLogin = view?.findViewById(R.id.btn_go)
        btnLogin?.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.btn_go-> loginSuccess()
            }
        }
    }

    private fun loginSuccess() {
        Handler().postDelayed({
            ProgressLoading.show(context)
            startActivity(Intent(activity,HomeActivity::class.java))
            activity?.finish()
        },1000)

    }


}