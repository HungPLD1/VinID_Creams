package com.example.vinid_icecreams.ui.fragment.home.store

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.utils.CommonUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.base.DialogClickListener
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.ui.adapter.adapterIndicator.AdapterSliderAd
import kotlinx.android.synthetic.main.fragment_store.*
import java.io.IOException
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class StoreFragment : BaseFragment<StoreViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var mListStore: ArrayList<Store> = ArrayList()
    private var mLocationManager: LocationManager? = null

    private val storeViewModel: StoreViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
    }

    private val storeController: StoreController by lazy {
        StoreController(
            ::toShopping
        )
    }

    override fun providerViewModel(): StoreViewModel = storeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun setUpUI(){
        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
        imgStoreLocation?.setOnClickListener(this)
        setupViewIndicatorAd()
        setupEpoxyRecycleView()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        handleGetListStore()
        storeViewModel.listStore.observe(viewLifecycleOwner, Observer { data ->
            ProgressLoading.dismiss()
            mListStore = data
            storeController.listStore = mListStore
        })

        storeViewModel.messageFail.observe(viewLifecycleOwner, Observer {
            showDiaLogFail(ERROR, it, object : DialogClickListener{
                override fun onConfirmClickListener() {
                    storeViewModel.getListStore()
                }
                override fun onCancelListener() {
                }

            })
        })
    }

    /*set up list store */
    private fun setupEpoxyRecycleView() {
        storeController.listStore = mListStore
        rcvStore.run {
            setItemSpacingDp(4)
            setController(storeController)
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgStoreLocation -> {
                    if (CommonUtils.instace.checkPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        ProgressLoading.show(context)
                        try {
                            mLocationManager?.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                0L,
                                0f,
                                locationListener
                            )
                        } catch (ex: SecurityException) {
                            Log.d("myTag", "Security Exception, no location available")
                        }
                    } else {
                        handleRequestPermission()
                    }
                }
            }
        }
    }

    private fun handleGetListStore() {
        if (isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            storeViewModel.getListStore()
        } else {
            showNoConnection(object : DialogClickListener {
                override fun onConfirmClickListener() {
                    storeViewModel.getListStore()
                }
                override fun onCancelListener() {
                }
            })
        }
    }

    //handle request permission
    private fun handleRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(permissions, CommonUtils.instace.REQUEST_CODE_PEMISSION)
    }

    /*define the listener*/
    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            Log.d("Location", "" + location.longitude + ":" + location.latitude)
            for (position in 0 until mListStore.size) {
                val range = CommonUtils.instace
                    .calculationByDistance(
                        location.latitude
                        , location.longitude
                        , mListStore[position].latitude
                        , mListStore[position].longitude
                    )
                mListStore[position].range = range
            }
            mListStore = CommonUtils.instace.sortList(mListStore)
            storeController.listStore = mListStore
            storeController.requestModelBuild()
            ProgressLoading.dismiss()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun setLocalName(longitude: Double, latitude: Double) {
        val geoCoder = Geocoder(context, Locale.getDefault()) //it is Geocoder

        val builder = StringBuilder()
        try {
            val mListAddress: List<Address> =
                geoCoder.getFromLocation(latitude, longitude, 1)
            val maxLines: Int = mListAddress[0].maxAddressLineIndex
            for (i in 0 until maxLines) {
                val addressStr: String = mListAddress[0].getAddressLine(i)
                builder.append(addressStr)
                builder.append(" ")
            }
            val address = builder.toString() //This is the complete address.

            if (address == "") {
                txtStoreLocation?.text = resources.getString(R.string.default_city)
            } else {
                txtStoreLocation?.text = address
            }
            ProgressLoading.dismiss()
        } catch (e: IOException) {
        } catch (e: NullPointerException) {
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CommonUtils.instace.REQUEST_CODE_PEMISSION -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
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
            }
        }
    }

    /*set up slider ad*/
    private fun setupViewIndicatorAd() {
        val mListAd: ArrayList<Int> = ArrayList()
        mListAd.add(R.drawable.green_ice)
        mListAd.add(R.drawable.ad_3)
        mListAd.add(R.drawable.green_2)
        val mSlideAdapter = AdapterSliderAd(requireContext(), mListAd)
        sliderStoreAd?.sliderAdapter = mSlideAdapter
    }


    private fun toShopping(position: Int) {
        CommonUtils.instace.saveStoreSelected(mListStore[position])
        CommonUtils.mListOrder = ArrayList()
        this.findNavController().navigate(R.id.fragmentShopping)
    }
}