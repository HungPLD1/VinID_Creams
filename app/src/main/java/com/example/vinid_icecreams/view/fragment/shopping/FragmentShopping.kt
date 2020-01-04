package com.example.vinid_icecreams.view.fragment.shopping


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterIceCream.AdapterIceCream
import com.example.vinid_icecreams.view.adapter.adapterIceCream.OnItemIceCreamClicklistener
import com.example.vinid_icecreams.view.fragment.cart.FragmentCart
import com.example.vinid_icecreams.view.fragment.details.FragmentDetails
import com.example.vinid_icecreams.view.fragment.store.FragmentStore


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener,OnItemIceCreamClicklistener , View.OnClickListener ,
    SearchView.OnQueryTextListener {
    private var rcvIceCream : RecyclerView? = null
    private var mListIceCream : ArrayList<IceCream> = ArrayList()
    private var spnFilterByType : Spinner?= null
    private var spnFilterByPrice : Spinner?= null
    private var spnFilterByDiscount : Spinner?= null
    private var imgBack : ImageView? = null
    private var btnCart : ImageView? = null
    private var mSvIceCream : SearchView? = null
    private var mAdapter : AdapterIceCream? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_shopping,container,false)
        iniView(mView)
        ProgressLoading.dismiss()
        setUpSpinerFilter()
        return mView
    }

    private fun iniView(mView: View) {
        activity?.actionBar?.title = resources.getString(R.string.home)
        rcvIceCream = mView.findViewById(R.id.rcvIceCream)
        spnFilterByType = mView.findViewById(R.id.spinerFilterByType)
        spnFilterByPrice = mView.findViewById(R.id.spinerFilterByPrice)
        spnFilterByDiscount = mView.findViewById(R.id.spinerFilterByDiscount)
        imgBack = mView.findViewById(R.id.imgBack)
        btnCart = mView.findViewById(R.id.imgShoppingToCart)
        mSvIceCream = mView.findViewById(R.id.svIcecream)
        imgBack?.setOnClickListener(this)
        btnCart?.setOnClickListener(this)
        mSvIceCream?.setOnQueryTextListener(this)
    }

    /*Set up and add data spiner filter*/
    private fun setUpSpinerFilter() {
        setupSpinerType()
        setupSpinerPrice()
        setupSpinerDiscount()
        setupListIcream()
    }

    /*Chú ý : Đang dummy data nên khi back lại fragment này sẽ k tìm được bundle nên sẽ bị null và crash*/
    private fun setupListIcream() {
        val bundle = arguments
        val mData = bundle?.getSerializable("DATA")
        Log.d("Hungpld",mData.toString())
        mListIceCream.addAll(mData as ArrayList<IceCream>)
        mAdapter = AdapterIceCream(context,mListIceCream,this)
        rcvIceCream?.layoutManager = GridLayoutManager(context,2)
        rcvIceCream?.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }


    private fun setupSpinerDiscount() {
        val mListType = arrayOf("Discount","10%","20%","50%")
        val mAdapterDiscount = context?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_item, // Layout
                mListType // Array
            )
        }
        spnFilterByDiscount?.adapter = mAdapterDiscount
        spnFilterByDiscount?.setSelection(0)
        spnFilterByDiscount?.onItemSelectedListener = this
    }


    override fun onItemClick(positon: Int) {
        val bundle = Bundle()
        val fragmentDetails = FragmentDetails()
        bundle.putSerializable("DETAILS", mListIceCream[positon])
        fragmentDetails.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.containerHome,fragmentDetails)?.addToBackStack("Hungpld")?.commit()
    }


    private fun setupSpinerPrice() {
        val mListType = arrayOf("Price","0 - 100K","100K - 200K","200K - 300K","300 - 400K","400K - 500K","> 500K")
        val mAdapterPrice = context?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_item, // Layout
                mListType // Array
            )
        }
        spnFilterByPrice?.adapter = mAdapterPrice
        spnFilterByPrice?.setSelection(0)
        spnFilterByPrice?.onItemSelectedListener = this
    }

    private fun setupSpinerType() {
        val mListType = arrayOf("Type","Chocolate","Matcha","Strawberry","Cacao","Vani","Other","Mix")
        val mAdapterType = context?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_dropdown_item, // Layout
                mListType // Array
            )
        }
        spnFilterByType?.adapter = mAdapterType
        spnFilterByType?.setSelection(0)
        spnFilterByType?.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when(parent){
                spnFilterByType -> handleLogicFileterByType()
                spnFilterByPrice ->handleLogicFileterByPrice()
                spnFilterByDiscount ->handleLogicFileterByDiscount()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun handleLogicFileterByDiscount() {
    }

    private fun handleLogicFileterByPrice() {
    }

    private fun handleLogicFileterByType() {
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.imgBack ->{
                    val fragmentStore = FragmentStore()
                    fragmentManager?.beginTransaction()?.replace(R.id.containerHome,fragmentStore)?.addToBackStack(null)?.commit()
                    ProgressLoading.show(context)
                }
                R.id.imgShoppingToCart->{
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

    override fun onQueryTextSubmit(query: String?): Boolean   {
        if (query == null || query.isEmpty()){
            setupListIcream()
        }else {
            setupListIcream()
            mAdapter?.filter(query.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isEmpty()){
            setupListIcream()
        }else {
            setupListIcream()
            mAdapter?.filter(newText.toLowerCase())
        }
        return false
    }


}
