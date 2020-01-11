package com.example.vinid_icecreams.view.fragment.shopping


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.SearchView
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
import org.angmarch.views.NiceSpinner
import kotlin.collections.ArrayList


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener,OnItemIceCreamClicklistener , View.OnClickListener ,
    SearchView.OnQueryTextListener {
    var TAG = FragmentShopping::class.java.name

    private var rcvIceCream : RecyclerView? = null
    private var mListIceCream : ArrayList<IceCream> = ArrayList()
    private var spnFilterByType : NiceSpinner?= null
    private var spnFilterByPrice : NiceSpinner?= null
    private var spnFilterByDiscount : NiceSpinner?= null
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
        observeData()
        iniView(mView)
        setUpSpinnerFilter()
        return mView
    }

    private fun iniView(mView: View) {
        activity?.actionBar?.title = resources.getString(R.string.home)
        rcvIceCream = mView.findViewById(R.id.rcv_IceCreamShopping)
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
        setupSpinnerType()
        setupSpinnerPrice()
        setupSpinnerDiscount()
    }

    /*observe data*/
    private fun observeData() {
        val bundle = arguments
        val id = bundle?.getInt("ID")
        id?.let { mViewModel.getListIceCream(it) }
        if (id == null){
            CommonUtils.instace.showSomeThingWentWrong(activity)
            return
        }
        ProgressLoading.show(context)
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            mViewModel.mListIceCream.observe(this, Observer { data ->
                mListIceCream.addAll(data)
                setupListIceCream(mListIceCream)
                ProgressLoading.dismiss()
            })
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    private fun setupListIceCream(mListIceCream :ArrayList<IceCream>){
        mListIceCream.addAll(mListIceCream)
        mAdapter = AdapterIceCream(context, mListIceCream, this)
        rcvIceCream?.layoutManager = GridLayoutManager(context, 2)
        rcvIceCream?.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }


    private fun setupSpinnerDiscount() {
        val mListType = listOf("Discount","10%","20%","50%")
        spnFilterByDiscount?.attachDataSource(mListType)
        spnFilterByDiscount?.setOnClickListener(this)
    }

    /*click item on list fragment */
    override fun onItemClick(positon: Int) {
        val bundle = Bundle()
        val fragmentDetails = FragmentDetails()
        bundle.putSerializable("DETAILS", mListIceCream[positon])
        fragmentDetails.arguments = bundle
        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,fragmentDetails)?.addToBackStack(null)?.commit()
    }


    private fun setupSpinnerPrice() {
        val mListType = listOf("Price","0 - 100K","100K - 200K","200K - 300K","300 - 400K","400K - 500K","> 500K")
        spnFilterByPrice?.attachDataSource(mListType)
        spnFilterByPrice?.setOnClickListener(this)
    }

    private fun setupSpinnerType() {
        val mListType = listOf("Type","Chocolate","Matcha","Strawberry","Cacao","Vani","Other","Mix")
        spnFilterByType?.attachDataSource(mListType)
        spnFilterByType?.setOnClickListener(this)
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
                    fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,fragmentStore)?.addToBackStack(null)?.commit()
                    ProgressLoading.show(context)
                }
                R.id.imgShoppingToCart->{
                    if (CommonUtils.instace.getOrderList()!!.size > 0){
                        val mFragmentCart = FragmentCart()
                        val tag = mFragmentCart.javaClass.name
                        ProgressLoading.show(context)
                        fragmentManager?.beginTransaction()?.replace(R.id.nav_host_fragment,mFragmentCart)?.addToBackStack(tag)?.commit()
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
            observeData()
        }else {
            setupListIceCream(mListIceCream)
            mAdapter?.filter(query.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isEmpty()) {
            observeData()
        }
        return false
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
