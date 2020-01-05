package com.example.vinid_icecreams.view.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import kotlinx.android.synthetic.main.fragment_login.view.*

class FragmentRegister : Fragment() {
    private var edtPhoneNumber : EditText? = null
    private var edtPassword : EditText? = null
    private var edtPasswordRepeat : EditText? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_register,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}