package com.example.vinid_icecreams.view.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.model.Order
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterComment.AdapterComment
import com.example.vinid_icecreams.view.adapter.adapterIndicator.AdapterViewPagerIndiCatorDetails
import com.example.vinid_icecreams.view.fragment.cart.FragmentCart
import com.google.android.material.button.MaterialButton
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_details.*


class FragmentDetails : Fragment(),View.OnClickListener {
    private var mIceCream : IceCream? = null

    private var mBtnBack : ImageView? = null
    private var mPager: ViewPager? = null
    private var mDotsIndicator : DotsIndicator? = null
    private var mTxtNameDetails : TextView? = null
    private var mTxtPriceDetails : TextView? = null
    private var rcvListComment : RecyclerView? = null
    private var mImgAddToCart : CircleImageView? = null
    private var mAdapterComment : AdapterComment? = null
    private var mBtnCart : ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_details,container,false)
        initView(view)
        getData()
        setupView()
        return view
    }

    private fun getData() {
        val bundle = arguments
        val mData = bundle?.getSerializable("DETAILS")
        mIceCream = mData as IceCream
    }

    private fun setupView() {
        txtNameDetails?.text = mIceCream?.name
        txtPriceDetails?.text = mIceCream?.price.toString()
        setupListComment()
        setupImagePager()

        mBtnBack?.setOnClickListener(this)
        mImgAddToCart?.setOnClickListener(this)
        mBtnCart?.setOnClickListener(this)
    }

    private fun setupImagePager() {
        val mListImage = ArrayList<String>()
        mIceCream?.mListImage?.let { mListImage.addAll(it) }
        val mAdapterViewPagerIndicatorAd = AdapterViewPagerIndiCatorDetails(context!!,mListImage)
        mPager!!.adapter = mAdapterViewPagerIndicatorAd
        mDotsIndicator?.setViewPager(mPager!!)

    }

    private fun setupListComment() {
        val mListComment = mIceCream?.listComment
        mAdapterComment = mListComment?.let { AdapterComment(context, it) }
        rcvListComment?.layoutManager = LinearLayoutManager(context)
        rcvListComment?.adapter = mAdapterComment

    }

    private fun initView(view: View) {
        mBtnBack = view.findViewById(R.id.imgBack)
        mPager = view.findViewById(R.id.mViewPagerDetails)
        mDotsIndicator = view.findViewById(R.id.mDotsIndicatorDetails)
        mTxtNameDetails = view.findViewById(R.id.txtNameDetails)
        mTxtPriceDetails = view.findViewById(R.id.txtPriceDetails)
        rcvListComment = view.findViewById(R.id.rcvListComment)
        mImgAddToCart = view.findViewById(R.id.imgAddToCart)
        mBtnCart = view.findViewById(R.id.btnCart)

    }

    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.imgBack ->{
//                    val mFragmentShopping = FragmentShopping()
//                    ProgressLoading.show(context)
//                    fragmentManager?.beginTransaction()?.replace(R.id.containerHome,mFragmentShopping)?.addToBackStack(null)?.commit()
                    activity?.onBackPressed()
                }
                R.id.imgAddToCart ->{
                    Toast.makeText(context,"Thêm vào giỏ hàng thành công",Toast.LENGTH_SHORT).show()
                    sendOrderToCart()
                }
                R.id.btnCart ->{
                    if (CommonUtils.instace.getOrderList()!!.size > 0){
                        val mFragmentCart = FragmentCart()
                        val tag = mFragmentCart.javaClass.name
                        ProgressLoading.show(context)
                        fragmentManager?.beginTransaction()?.replace(R.id.containerHome,mFragmentCart)?.addToBackStack(tag)?.commit()
                    }else{
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
        val order = Order(mIceCream!!,1,0)
        CommonUtils.instace.setOrderToList(order)
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = resources.getString(R.string.home)
    }
}