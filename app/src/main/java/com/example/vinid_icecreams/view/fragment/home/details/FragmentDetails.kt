package com.example.vinid_icecreams.view.fragment.home.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Comment
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.view.adapter.adapterComment.AdapterComment
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndiCatorDetails
import kotlinx.android.synthetic.main.fragment_details.*


class FragmentDetails : Fragment(), View.OnClickListener {
    private var mIceCream: IceCream? = null
    private var mListComment: ArrayList<Comment>? = ArrayList()
    private var mAdapterComment: AdapterComment? = null

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
        setupBackDevice()
        getIceCream()
        setupView()
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentShopping)
        }
    }

    /*get Id ice cream*/
    private fun getIceCream() {
        mIceCream = arguments?.getSerializable("DETAILS") as IceCream?
    }

    /*observe data*/
//    private fun observeData() {
//        if (CommonUtils.instace.isConnectToNetwork(context)) {
//            ProgressLoading.show(context)
//            mViewModel.getDetailsIceCream(mIdIceCream!!)
//            mViewModel.mIceCream.observe(this, Observer { data ->
//                mIceCream = data
//                setupView()
//                ProgressLoading.dismiss()
//            })
//        }else{
//            ProgressLoading.dismiss()
//            showNoConnection()
//        }
//    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        txtDetailsNameOrder?.text = mIceCream?.name
        txtDetailsPrice?.text = mIceCream?.price.toString() + " $"
        setupListComment()
        setupImagePager()
        setupRattingBar()
    }

    private fun setupRattingBar() {
        mListComment = mIceCream?.listComment
        val mListRatingBar = ArrayList<Int>()
        for (i in 0 until mListComment!!.size) {
            mListComment?.get(i)?.rating_star?.let { mListRatingBar.add(it) }
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
        mIceCream?.image_paths?.let { mListImage.addAll(it) }
        val mAdapterViewPagerIndicatorAd = AdapterViewPagerIndiCatorDetails(context!!, mListImage)
        viewPagerDetails!!.adapter = mAdapterViewPagerIndicatorAd
        dotsIndicatorDetails?.setViewPager(viewPagerDetails!!)

    }

    private fun setupListComment() {
        mListComment = mIceCream?.listComment
        mAdapterComment = mListComment?.let { AdapterComment(context, it) }
        rcvDetailsListComment?.layoutManager = LinearLayoutManager(context)
        rcvDetailsListComment?.adapter = mAdapterComment

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
        val order = Order(mIceCream!!, 1, 0)
        CommonUtils.instace.setOrderToList(order)
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}