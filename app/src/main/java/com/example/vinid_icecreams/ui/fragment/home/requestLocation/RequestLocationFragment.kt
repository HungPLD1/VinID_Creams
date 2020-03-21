package com.example.vinid_icecreams.ui.fragment.home.requestLocation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.extension.isDeniedPermanently
import com.example.vinid_icecreams.extension.openPermissionSettings
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.utils.ProgressLoading
import kotlinx.android.synthetic.main.fragment_request_location.*
import javax.inject.Inject

class RequestLocationFragment : BaseFragment<RequestLocationViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: RequestLocationViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(RequestLocationViewModel::class.java)
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)
    }

    private var mLocationManager: LocationManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_request_location, container, false)
    }

    override fun providerViewModel(): RequestLocationViewModel = viewModel

    override fun setUpUI() {
        if (!isPermissionGranted()){
            setupPermissionGuideline(
                isDeniedPermanently(Manifest.permission.ACCESS_FINE_LOCATION)
            )
        }else{
            ProgressLoading.show(context)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RC_LOCATION)
        }

    }

    override fun setupViewModel() {
        super.setupViewModel()
    }

    override fun onResume() {
        super.onResume()
        if (isPermissionGranted()) { // permission granted -> let's go
            ProgressLoading.show(context)
            requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), RC_LOCATION)
        }
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
                    try {
                        ProgressLoading.show(context)
                        mLocationManager?.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            0L,
                            0f,
                            locationListener
                        )
                    } catch (ex: SecurityException) {
                        Log.d("myTag", "Security Exception, no location available")
                    }
                }
            } else {
                if (isAdded) {
                    setupPermissionGuideline(
                        isDeniedPermanently(Manifest.permission.ACCESS_FINE_LOCATION)
                    )
                }
            }
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

    private fun isPermissionGranted(): Boolean =
        ContextCompat.checkSelfPermission(requireContext()
            , Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED

    private fun toStore() {
        this.findNavController().navigate(R.id.fragmentStore)
    }

    /*define the listener*/
    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            homeViewModel.setLocation(location)
            toStore()
            ProgressLoading.dismiss()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    companion object {
        const val RC_LOCATION = 0x111
    }
}