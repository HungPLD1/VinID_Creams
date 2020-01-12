package com.example.vinid_icecreams.view.fragment.home.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Comment
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterComment.AdapterComment
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndiCatorDetails
import com.example.vinid_icecreams.view.fragment.home.cart.FragmentCart
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import de.hdodenhof.circleimageview.CircleImageView


class FragmentDetails : Fragment(), View.OnClickListener {
    private var mIceCream: IceCream? = null
    private var mListComment: ArrayList<Comment>? = ArrayList()
    private var mIdIceCream : Int? = null

    private var mRatting: RatingBar? = null
    private var mBtnBack: ImageView? = null
    private var mPager: ViewPager? = null
    private var mDotsIndicator: DotsIndicator? = null
    private var mTxtNameDetails: TextView? = null
    private var mTxtPriceDetails: TextView? = null
    private var rcvListComment: RecyclerView? = null
    private var mImgAddToCart: CircleImageView? = null
    private var mAdapterComment: AdapterComment? = null
    private var mBtnCart: ImageView? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details, container, false)
        initView(view)
        getIDIceCream()
        observeData()
        return view
    }

    /*get Id ice cream*/
    private fun getIDIceCream() {
        val bundle = arguments
        mIdIceCream = bundle?.getInt("DETAILS_ID")
    }

    /*observe data*/
    private fun observeData() {
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getDetailsIceCream(mIdIceCream!!)
            mViewModel.mIceCream.observe(this, Observer { data ->
                mIceCream = data
                setupView()
                ProgressLoading.dismiss()
            })
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        mTxtNameDetails?.text = mIceCream?.name
        mTxtPriceDetails?.text = mIceCream?.price.toString() + " $"
        setupListComment()
        setupImagePager()
        setupRattingBar()

        mBtnBack?.setOnClickListener(this)
        mImgAddToCart?.setOnClickListener(this)
        mBtnCart?.setOnClickListener(this)
    }

    private fun setupRattingBar() {
        mListComment = mIceCream?.listComment
        val mListRatingBar = ArrayList<Int>()
        for (i in 0 until mListComment!!.size) {
            mListComment?.get(i)?.rating_star?.let { mListRatingBar.add(it) }
        }
        when (mListRatingBar.size) {
            0 -> mRatting?.rating = 0F
            1 -> mRatting?.rating = mListRatingBar[0].toFloat()
            else -> {
                mRatting?.rating = CommonUtils.instace.calculateAverage(mListRatingBar)
            }
        }
    }

    private fun setupImagePager() {
        val mListImage = ArrayList<String>()
        mIceCream?.image_paths?.let { mListImage.addAll(it) }
        val mAdapterViewPagerIndicatorAd = AdapterViewPagerIndiCatorDetails(context!!, mListImage)
        mPager!!.adapter = mAdapterViewPagerIndicatorAd
        mDotsIndicator?.setViewPager(mPager!!)

    }

    private fun setupListComment() {
        mListComment = mIceCream?.listComment
        mAdapterComment = mListComment?.let { AdapterComment(context, it) }
        rcvListComment?.layoutManager = LinearLayoutManager(context)
        rcvListComment?.adapter = mAdapterComment

    }

    private fun initView(view: View) {
        mBtnBack = view.findViewById(R.id.imgBack)
        mPager = view.findViewById(R.id.mViewPagerDetails)
        mDotsIndicator = view.findViewById(R.id.mDotsIndicatorDetails)
        mTxtNameDetails = view.findViewById(R.id.txt_NameDetails)
        mTxtPriceDetails = view.findViewById(R.id.txt_PriceDetails)
        rcvListComment = view.findViewById(R.id.rcvListComment)
        mImgAddToCart = view.findViewById(R.id.imgAddToCart)
        mBtnCart = view.findViewById(R.id.btnCart)
        mRatting = view.findViewById(R.id.ratting_details)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.imgBack -> {
                    activity?.onBackPressed()
                }
                R.id.imgAddToCart -> {
                    Toast.makeText(context, "Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT)
                        .show()
                    sendOrderToCart()
                }
                R.id.btnCart -> {
                    if (CommonUtils.instace.getOrderList()!!.size > 0) {
                        val mFragmentCart = FragmentCart()
                        val tag = mFragmentCart.javaClass.name
                        ProgressLoading.show(context)
                        fragmentManager?.beginTransaction()
                            ?.replace(R.id.nav_host_fragment, mFragmentCart)?.addToBackStack(tag)
                            ?.commit()
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
                observeData()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}