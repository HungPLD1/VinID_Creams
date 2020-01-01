package com.example.vinid_icecreams.view.fragment.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinid_icecreams.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FragmentGoogleMap : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_googlemap,container,false)
        return view
    }
}