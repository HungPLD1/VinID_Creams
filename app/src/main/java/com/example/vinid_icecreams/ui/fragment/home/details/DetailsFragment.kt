package com.example.vinid_icecreams.ui.fragment.home.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.Comment
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.ui.adapter.adapterIndicator.AdapterViewPagerIndiCatorDetails
import com.example.vinid_icecreams.utils.ProgressLoading
import kotlinx.android.synthetic.main.fragment_details.*
import javax.inject.Inject

class DetailsFragment : BaseFragment<DetailsViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val detailsViewModel: DetailsViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(DetailsViewModel::class.java)
    }

    private val detailController: DetailsController by lazy { DetailsController() }

    private var iceCream: IceCream? = null

    private var listComment: ArrayList<Comment>? = ArrayList()

    override fun provideViewModel(): DetailsViewModel = detailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
        setupBackDevice()
        setupView()
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentShopping)
        }
    }

    /*get Id ice cream*/
    private fun getIDIceCream() = arguments?.getSerializable("DETAILS") as Int


    private fun observeData() {
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            detailsViewModel.getDetailsIceCream(getIDIceCream())
            detailsViewModel.iceCream.observe(viewLifecycleOwner, Observer { data ->
                iceCream = data
                ProgressLoading.dismiss()
            })
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        txtDetailsNameOrder?.text = iceCream?.name
        txtDetailsPrice?.text = iceCream?.price.toString() + " $"
        setupListComment()
        setupImagePager()
        setupRattingBar()
    }

    private fun setupRattingBar() {
        listComment = iceCream?.listComment
        val mListRatingBar = ArrayList<Int>()
        for (i in 0 until listComment?.size!!) {
            listComment?.get(i)?.rating_star?.let { mListRatingBar.add(it) }
        }
        when (mListRatingBar.size) {
            0 -> rattingDetails?.rating = 0F
            1 -> rattingDetails?.rating = mListRatingBar[0].toFloat()
            else -> {
                rattingDetails?.rating = CommonUtils.instace.calculateAverage(mListRatingBar)
            }
        }
    }

    private fun setupImagePager() {
        val mListImage = ArrayList<String>()
        iceCream?.image_paths?.let { mListImage.addAll(it) }
        val mAdapterViewPagerIndicatorAd = AdapterViewPagerIndiCatorDetails(context!!, mListImage)
        viewPagerDetails!!.adapter = mAdapterViewPagerIndicatorAd
        dotsIndicatorDetails?.setViewPager(viewPagerDetails!!)

    }

    private fun setupListComment() {
        detailController.listComment = iceCream?.listComment
        rcvDetailsListComment.setController(detailController)
    }

    private fun initView() {
        imgDetailsBack?.setOnClickListener(this)
        imgDetailsAddToCart?.setOnClickListener(this)
        btnDetailsCart?.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imgDetailsBack -> {
                    this.findNavController().navigate(R.id.fragmentShopping)
                }
                R.id.imgDetailsAddToCart -> {
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT)
                        .show()
                    sendOrderToCart()
                }
                R.id.btnDetailsCart -> {
                    if (CommonUtils.instace.getOrderList()!!.size > 0) {
                        findNavController().navigate(R.id.fragmentCart)
                    } else {
                        val pDialog = KAlertDialog(context, KAlertDialog.WARNING_TYPE)
                        pDialog.titleText = "Giỏ hàng trống"
                        pDialog.setCancelable(true)
                        pDialog.show()
                    }
                }
            }
        }
    }

    private fun sendOrderToCart() {
        val order = Order(iceCream!!, 1, 0)
        CommonUtils.instace.setOrderToList(order)
    }
}