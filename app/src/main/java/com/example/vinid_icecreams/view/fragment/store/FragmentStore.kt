package com.example.vinid_icecreams.view.fragment.store

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.mock.MockData
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndicator
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndicatorAd
import com.example.vinid_icecreams.view.adapter.adapterStore.AdapterStore
import com.example.vinid_icecreams.view.adapter.adapterStore.OnItemStoreClicklistener
import com.example.vinid_icecreams.view.fragment.shopping.FragmentShopping
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_store.*

class FragmentStore : Fragment(), View.OnClickListener, OnItemStoreClicklistener {
    private var mListStore: ArrayList<Store> = ArrayList()
    private var mLocationManager: LocationManager? = null
    private var mImgLocation: ImageView? = null
    private var mTxtLocation: TextView? = null
    private var mPagerAd: ViewPager? = null
    private var mDotsIndicator : DotsIndicator? = null

    private var mRcvStore: RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store, container, false)
        ProgressLoading.dismiss()
        initView(view)
        setupViewIndicatorAd()
        setupView()
        return view
    }

    private fun initView(view: View?) {
        mRcvStore = view?.findViewById(R.id.rcvStore)
        mImgLocation = view?.findViewById(R.id.imgLocation)
        mTxtLocation = view?.findViewById(R.id.txtLocation)
        mPagerAd = view?.findViewById(R.id.mViewPagerAd)
        mDotsIndicator = view?.findViewById(R.id.mDotsIndicatorAd)

        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
        mImgLocation?.setOnClickListener(this)
    }

    private fun setupView() {
        setupListStore()
    }

    private fun setupListStore() {
        mListStore.addAll(MockData.getListStore())
        val mAdapterStore = AdapterStore(context, mListStore, this)
        mRcvStore?.layoutManager = LinearLayoutManager(context)
        mRcvStore?.adapter = mAdapterStore
    }

    override fun onItemClick(positon: Int) {
        val mFragmentShopping = FragmentShopping()
        val bundle = Bundle()
        bundle.putSerializable("DATA", mListStore[positon].mListIceCream)
        mFragmentShopping.arguments = bundle
        ProgressLoading.show(context)
        fragmentManager?.beginTransaction()?.addToBackStack(null)?.replace(R.id.containerHome, mFragmentShopping)
            ?.commit()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.imgLocation -> {
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
            }
        }
    }

    //handle reuqest permission
    private fun handleRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                permissions,
                CommonUtils.instace.REQUEST_CODE_PEMISSION
            )
        }
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            ProgressLoading.dismiss()
            mTxtLocation?.text = ("" + location.longitude + ":" + location.latitude)
        }

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
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

    /*set up view indicator ad*/
    private fun setupViewIndicatorAd() {
        val mListAd: ArrayList<Int> = ArrayList()
        mListAd.add(R.drawable.first_ad)
        mListAd.add(R.drawable.second_ad)
        mListAd.add(R.drawable.last_ad)
        val mAdapterViewPagerIndicatorAd = AdapterViewPagerIndicatorAd(context!!,mListAd)
        mPagerAd!!.adapter = mAdapterViewPagerIndicatorAd
        mDotsIndicator?.setViewPager(mPagerAd!!)

    }
}
