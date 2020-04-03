package com.example.vinid_icecreams.ui.fragment.home.store

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.DialogClickListener
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.ui.adapter.adapterIndicator.AdapterSliderAd
import com.example.vinid_icecreams.ui.fragment.home.map.MapFragment
import com.example.vinid_icecreams.utils.ProgressLoading
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.android.synthetic.main.fragment_store.*
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


class StoreFragment : BaseFragment<StoreViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private var listStore: ArrayList<Store> = ArrayList()

    private val viewModel: StoreViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(StoreViewModel::class.java)
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(requireActivity(), viewModelFactory).get(HomeViewModel::class.java)
    }

    private val storeController: StoreController by lazy {
        StoreController(
            ::toShopping
        )
    }

    override fun providerViewModel(): StoreViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_store, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        setupViewIndicatorAd()
        setupEpoxyRecycleView()
        setupLocation()
    }

    private fun setupLocation() {
        imgStoreLocation?.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setAddressUser(location: Location?) {
        if (location != null) {
            val geoCoder = Geocoder(requireContext(), Locale.getDefault())
            val addresses: List<Address> = geoCoder.getFromLocation(
                location.latitude
                , location.longitude, 1
            )
            val cityName: String = addresses[0].getAddressLine(0)
            txtUserLocation.text = " $cityName "
        }else{
            txtUserLocation.text = "K xác định được vị trí"
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.getLocation()
        getListStore()
        viewModel.location.observe(viewLifecycleOwner, Observer {
            setAddressUser(it)
        })

        viewModel.listStore.observe(viewLifecycleOwner, Observer { data ->
            storeController.listStore = data
            listStore = data
        })

        viewModel.messageFailed.observe(viewLifecycleOwner, Observer {
            showDiaLogFailed(ERROR, it, object : DialogClickListener {
                override fun onConfirmClickListener() {
                    viewModel.getListStore()
                }

                override fun onCancelListener() {
                }

            })
        })
    }

    /*set up list store */
    private fun setupEpoxyRecycleView() {
        rcvStore.run {
            setItemSpacingDp(4)
            setController(storeController)
        }
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view) {
                imgStoreLocation -> {
                    showBottomSheetDiaLogMap()
                }
            }
        }
    }

    private fun getListStore() {
        if (isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            viewModel.getListStore()
        } else {
            showNoConnection(object : DialogClickListener {
                override fun onConfirmClickListener() {
                    viewModel.getListStore()
                }

                override fun onCancelListener() {
                }
            })
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

    private fun showBottomSheetDiaLogMap() {
        val bottomSheetDialogFragment = MapFragment()
        bottomSheetDialogFragment
            .show(requireActivity().supportFragmentManager, bottomSheetDialogFragment.tag)
    }


    private fun toShopping(position: Int) {
        homeViewModel.setStoreSelection(listStore[position])
        homeViewModel.resetOrder()
        this.findNavController().navigate(R.id.fragmentShopping)
    }
}
