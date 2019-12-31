package com.example.vinid_icecreams.view.fragment.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R

class FragmentChat : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = layoutInflater.inflate(R.layout.fragment_chat,container,false)
        return view
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = resources.getString(R.string.chat)
    }
}