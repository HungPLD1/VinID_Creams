package com.example.vinid_icecreams.view.fragment.store

import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.view.adapter.adapterStore.AdapterStore
import com.example.vinid_icecreams.view.adapter.adapterStore.OnItemStoreClicklistener
import kotlinx.android.synthetic.main.fragment_store.*

class FragmentStore : Fragment(),View.OnClickListener ,OnItemStoreClicklistener {
    var mListStore : ArrayList<Store> = ArrayList()
    var mLocationManager : LocationManager? = null
    var mImgLocation : ImageView? = null

    var mRcvStore : RecyclerView? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store,container,false)
        initView(view)
        setupView()
        return view
    }

    private fun initView(view: View?) {
        mRcvStore = view?.findViewById(R.id.rcvStore)
        mImgLocation = view?.findViewById(R.id.imgLocation)
        mLocationManager = context?.getSystemService(LOCATION_SERVICE) as LocationManager?
        mImgLocation?.setOnClickListener(this)
    }

    private fun setupView() {
        setupListStore()
    }

    private fun setupListStore() {
        val mAdapterStore = AdapterStore(context, mListStore, this )
        mRcvStore?.adapter = mAdapterStore
    }

    override fun onItemClick(positon: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onClick(view: View?) {
        if(view != null){
            when(view.id){
                R.id.imgLocation ->{
                    try {
                        // Request location updates
                        Toast.makeText(context,"Click",Toast.LENGTH_SHORT).show()
                        mLocationManager?.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0f, locationListener)
                    } catch(ex: SecurityException) {
                        Log.d("myTag", "Security Exception, no location available")
                    }
                }
            }
        }
    }

    //define the listener
    private val locationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            txtLocation.text = ("" + location.longitude + ":" + location.latitude)
        }
        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
        override fun onProviderEnabled(provider: String) {}
        override fun onProviderDisabled(provider: String) {}
    }
}
