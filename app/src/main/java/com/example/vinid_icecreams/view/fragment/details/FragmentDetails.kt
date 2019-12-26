package com.example.vinid_icecreams.view.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.fragment.shopping.FragmentShopping
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_details.*

class FragmentDetails : Fragment(),View.OnClickListener {
    private var mIceCream : IceCream? = null

    private var mBtnBack : ImageView? = null
    private var mImageDetails: ImageView? = null
    private var mTxtNameDetails : TextView? = null
    private var mTxtPriceDetails : TextView? = null
    private var rcvListComment : RecyclerView? = null
    private var mImgAddToCart : CircleImageView? = null

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
        Picasso.with(context).load(mIceCream?.imagePath)
            .placeholder(R.drawable.loading_image)
            .error(R.drawable.default_image)
            .into(mImageDetails)
        txtNameDetails?.text = mIceCream?.name
        txtPriceDetails?.text = mIceCream?.price.toString()

        mBtnBack?.setOnClickListener(this)
        mImgAddToCart?.setOnClickListener(this)
    }

    private fun initView(view: View) {
        mBtnBack = view.findViewById(R.id.imgBack)
        mImageDetails = view.findViewById(R.id.imgDetails)
        mTxtNameDetails = view.findViewById(R.id.txtNameDetails)
        mTxtPriceDetails = view.findViewById(R.id.txtPriceDetails)
        rcvListComment = view.findViewById(R.id.rcvListComment)
        mImgAddToCart = view.findViewById(R.id.imgAddToCart)

    }

    override fun onClick(v: View?) {
        if (v != null){
            when(v.id){
                R.id.imgBack ->{
                    val mFragmentShopping = FragmentShopping()
                    ProgressLoading.show(context)
                    fragmentManager?.beginTransaction()?.replace(R.id.containerHome,mFragmentShopping)?.addToBackStack(null)?.commit()
                }
                R.id.imgAddToCart ->{

                }
            }
        }
    }
}