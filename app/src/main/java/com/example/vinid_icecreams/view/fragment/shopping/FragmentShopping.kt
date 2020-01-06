package com.example.vinid_icecreams.view.fragment.shopping


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener,OnItemIceCreamClicklistener , View.OnClickListener ,
    SearchView.OnQueryTextListener {
    var TAG = FragmentShopping::class.java.name

    private var rcvIceCream : RecyclerView? = null
    private var mListIceCream : ArrayList<IceCream> = ArrayList()
    private var spnFilterByType : Spinner?= null
    private var spnFilterByPrice : Spinner?= null
    private var spnFilterByDiscount : Spinner?= null
    private var imgBack : ImageView? = null
    private var btnCart : ImageView? = null
    private var mSvIceCream : SearchView? = null
    private var mAdapter : AdapterIceCream? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_shopping,container,false)
        iniView(mView)
        ProgressLoading.dismiss()
        setUpSpinnerFilter()
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

    /*Set up and add data spinner filter*/
    private fun setUpSpinnerFilter() {
        setupSpinerType()
        setupSpinerPrice()
        setupSpinerDiscount()
        setupListIceCream()
    }

    /*observe data*/
    private fun setupListIceCream() {
        val bundle = arguments
        val id = bundle?.getInt("ID")
        id?.let { mViewModel.getListIceCream(it) }
        if (id == null){
            CommonUtils.instace.showSomeThingWentWrong(activity)
            return
        }
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            mViewModel.mListIceCream.observe(this, Observer { data ->
                mListIceCream.addAll(data)
                mAdapter = AdapterIceCream(context, mListIceCream, this)
                rcvIceCream?.layoutManager = GridLayoutManager(context, 2)
                rcvIceCream?.adapter = mAdapter
                mAdapter!!.notifyDataSetChanged()
            })
        }
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
            setupListIceCream()
        }else {
            setupListIceCream()
            mAdapter?.filter(query.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isEmpty()){
            setupListIceCream()
        }else {
            setupListIceCream()
            mAdapter?.filter(newText.toLowerCase())
        }
        return false
    }


}
