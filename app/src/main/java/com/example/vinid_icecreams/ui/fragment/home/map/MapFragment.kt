package com.example.vinid_icecreams.ui.fragment.home.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.BaseBottomSheetDiaFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetBehavior
import javax.inject.Inject

class MapFragment : BaseBottomSheetDiaFragment<MapViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val mapViewModel: MapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
    }

    override fun providerViewModel(): MapViewModel = mapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLoading()
        setupBottomSheetDiaLog()
    }

    private fun setupBottomSheetDiaLog() {
        dialog?.setOnShowListener {
            dialog?.findViewById<FrameLayout>(R.id.design_bottom_sheet)?.let { frameLayout ->
                val bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
                val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
                    override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        bottomSheetBehavior.skipCollapsed = true
                    }
                }
                bottomSheetBehavior.setBottomSheetCallback(bottomSheetCallback)
            }
        }
    }
}