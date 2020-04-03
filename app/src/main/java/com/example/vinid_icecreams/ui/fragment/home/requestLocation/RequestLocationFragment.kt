package com.example.vinid_icecreams.ui.fragment.home.requestLocation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.extension.isDeniedPermanently
import com.example.vinid_icecreams.extension.openPermissionSettings
import com.example.vinid_icecreams.utils.ProgressLoading
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_request_location.*
import javax.inject.Inject

class RequestLocationFragment : BaseFragment<RequestLocationViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RequestLocationViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RequestLocationViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_location, container, false)
    }

    override fun providerViewModel(): RequestLocationViewModel = viewModel

    override fun setUpUI() {
        super.setUpUI()
        setupPermissionGuideline(false)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RC_LOCATION && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (isAdded) {
                    getLocation()
                }
            } else {
                if (isAdded) {
                    setupPermissionGuideline(
                        isDeniedPermanently(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                } else {
                    activity?.finish()
                }
            }
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.isSaveLocationSuccess.observe(viewLifecycleOwner, Observer {
            if (it){
                toStore()
            }else{
                getLocation()
            }
        })
    }

    private fun getLocation() {
        ProgressLoading.show(context)
        val fusedLocationClient: FusedLocationProviderClient? =
            LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient?.lastLocation?.addOnSuccessListener {
            viewModel.saveLocation(it)
            ProgressLoading.dismiss()
        }
    }

    private fun setupPermissionGuideline(shouldOpenSetting: Boolean) {
        if (shouldOpenSetting) {
            tvExplain.setText(R.string.request_location_explain_forever_desc)
            btnRequestLocation.setText(R.string.scan_qr_code_permission_forever_action)
            btnRequestLocation.setOnClickListener {
                activity?.openPermissionSettings()
            }
        } else {
            tvExplain.setText(R.string.request_location_explain)
            btnRequestLocation.setText(R.string.allow_to_access)
            btnRequestLocation.setOnClickListener {
                requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RC_LOCATION)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isPermissionGranted()) {
            getLocation()
        }
    }


    private fun isPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(
            requireContext()
            , Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

    private fun toStore() {
        ProgressLoading.dismiss()
        this.findNavController().navigate(R.id.fragmentStore)
    }

    companion object {
        const val RC_LOCATION = 0
    }
}