package com.example.vinid_icecreams.view.fragment.home.store

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
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.utils.CommonUtils
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterSliderAd
import com.example.vinid_icecreams.view.adapter.adapterStore.AdapterStore
import com.example.vinid_icecreams.view.adapter.adapterStore.OnItemStoreClicklistener
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import com.smarteist.autoimageslider.SliderView
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList




class FragmentStore : Fragment(), View.OnClickListener, OnItemStoreClicklistener {
    var TAG = FragmentStore::class.java.name

    private var mListStore: ArrayList<Store> = ArrayList()
    private var mLocationManager: LocationManager? = null
    private var mImgLocation: ImageView? = null
    private var mTxtLocation: TextView? = null
    private var mSliderAd: SliderView? = null
    private var mBtnGoToCart : ImageView? = null
    private var mAdapterStore : AdapterStore? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }


    private var mRcvStore: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)
        initView(view)
        setupViewIndicatorAd()
        setupView()
        return view
    }

    private fun initView(view: View?) {
        activity?.actionBar?.title = resources.getString(R.string.home)
        mRcvStore = view?.findViewById(R.id.rcv_store)
        mImgLocation = view?.findViewById(R.id.img_store_location)
        mTxtLocation = view?.findViewById(R.id.txt_Store_Location)
        mSliderAd = view?.findViewById(R.id.slider_ad)

        /*click event*/
        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
        mImgLocation?.setOnClickListener(this)
        mBtnGoToCart?.setOnClickListener(this)

        mTxtLocation?.setOnClickListener(this)
    }

    private fun setupView() {
        observeData()
    }

    /*observe data*/
    private fun observeData() {
        if(CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getListStore()
            mViewModel.mListStore.observe(viewLifecycleOwner, Observer { data ->
                ProgressLoading.dismiss()
                mListStore = data
                setupListStore(mListStore)
            })
        }else{
            showNoConnection()
        }
    }

    /*set up list store */
    private fun setupListStore (mListStore: ArrayList<Store>){
        mAdapterStore  = AdapterStore(context, mListStore, this)
        mRcvStore?.layoutManager = LinearLayoutManager(context)
        mRcvStore?.adapter = mAdapterStore
        mAdapterStore?.notifyDataSetChanged()
    }

    /*click on list store*/
    override fun onItemClick(positon: Int) {
        CommonUtils.instace.saveStoreSelected(mListStore[positon])
        CommonUtils.mListOrder = ArrayList()
        this.findNavController().navigate(R.id.fragmentShopping)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.img_store_location -> {
                    if (CommonUtils.instace.checkPermission(
                            context!!,
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
                R.id.txt_Store_Location ->{
                }
            }
        }
    }

    //handle reuqest permission
    private fun handleRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        requestPermissions(permissions, CommonUtils.instace.REQUEST_CODE_PEMISSION)
    }

    /*define the listener*/
    private val locationListener: LocationListener = object : LocationListener {
        @SuppressLint("SetTextI18n")
        override fun onLocationChanged(location: Location) {
            Log.d("Location","" + location.longitude + ":" + location.latitude)
            //setLocalName(location.longitude,location.latitude)
            for (position in 0 until mListStore.size){
                val range  = CommonUtils.instace.calculationByDistance(location.latitude,location.longitude,mListStore[position].latitude,mListStore[position].longitude)
                mListStore[position].range = range
            }
            mListStore = CommonUtils.instace.sortList(mListStore)
            setupListStore(mListStore)
            ProgressLoading.dismiss()
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }

    private fun setLocalName(longitude: Double, latitude: Double){
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

            if (address == ""){
                mTxtLocation?.text = resources.getString(R.string.default_city)
            }else{
                mTxtLocation?.text = address
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
            else -> {

            }
        }
    }

    /*set up slider ad*/
    private fun setupViewIndicatorAd() {
        val mListAd: ArrayList<Int> = ArrayList()
        mListAd.add(R.drawable.green_ice)
        mListAd.add(R.drawable.ad_3)
        mListAd.add(R.drawable.green_2)
        val mSlideAdapter = AdapterSliderAd(context!!,mListAd)
        mSliderAd?.sliderAdapter = mSlideAdapter
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                observeData()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

}
