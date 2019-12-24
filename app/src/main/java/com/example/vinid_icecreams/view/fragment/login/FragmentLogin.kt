package com.example.vinid_icecreams.view.fragment.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import kotlinx.android.synthetic.main.fragment_login.*


class FragmentLogin : Fragment() {
    var mEmail : String = ""
    var mPassword : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_login,container,false)
        initView()
        return view
    }

    private fun initView() {
        edt_username.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validateEmail(edt_username.text.toString())
            }
        })

        edt_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                validatePassword(edt_password.text.toString())
            }
        })
    }

    private fun validateEmail(email : String) {

    }

    private fun validatePassword(password : String) {

    }


}