package com.example.vinid_icecreams.view.fragment.home.shopping


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterIceCream.AdapterIceCream
import com.example.vinid_icecreams.view.adapter.adapterIceCream.OnItemIceCreamClicklistener
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream
import kotlinx.android.synthetic.main.fragment_shopping.*
import kotlin.collections.ArrayList


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener,OnItemIceCreamClicklistener , View.OnClickListener ,
    SearchView.OnQueryTextListener {
    var TAG = FragmentShopping::class.java.name

    private var mListIceCream : ArrayList<IceCream> = ArrayList()
    private var mAdapter : AdapterIceCream? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setupBackDevice()
        setUpSpinnerFilter()
        observeData()
        handleGetListIceCream()
    }

    private fun initView() {
        /*handle onclick*/
        imgShoppingBack.setOnClickListener(this)
        imgShoppingToCart.setOnClickListener(this)
    }

    private fun setupBackDevice() {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            findNavController().navigate(R.id.fragmentStore)
        }
    }


    /*Set up and add data spinner filter*/
    private fun setUpSpinnerFilter() {
        setupSpinnerType()
        setupSpinnerPrice()
        setupSpinnerDiscount()
    }

    /*handle get list ice cream*/
    private fun handleGetListIceCream() {
        val id = CommonUtils.mSelectedStore?.id
        if (id == null){
            CommonUtils.instace.showSomeThingWentWrong(activity)
            return
        }
        ProgressLoading.show(context)
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            mViewModel.getListIceCream(id)
        }else{
            ProgressLoading.dismiss()
            showNoConnection()
        }
    }

    /*observe data*/
    private fun observeData(){
        mViewModel.mListIceCream.observe(viewLifecycleOwner, Observer { data ->
            mListIceCream = data
            setupListIceCream(mListIceCream)
            ProgressLoading.dismiss()
        })
    }

    private fun setupListIceCream(mData :ArrayList<IceCream>){
        mListIceCream = mData
        mAdapter = AdapterIceCream(context, mListIceCream, this)
        rcvShoppingIceCream?.layoutManager = GridLayoutManager(context, 2)
        rcvShoppingIceCream?.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()
    }


    private fun setupSpinnerDiscount() {
        val mListType = listOf("Discount","10%","20%","50%")
        spnShoppingFilterByDiscount?.attachDataSource(mListType)
        spnShoppingFilterByDiscount?.setOnClickListener(this)
    }

    /*click item on list fragment */
    override fun onItemClick(positon: Int) {
        val bundle = bundleOf("DETAILS" to mListIceCream[positon])
        findNavController().navigate(R.id.fragmentDetails,bundle)
    }


    private fun setupSpinnerPrice() {
        val mListType = listOf("Price","0 - 100K","100K - 200K","200K - 300K","300 - 400K","400K - 500K","> 500K")
        spnShoppingFilterByPrice?.attachDataSource(mListType)
        spnShoppingFilterByPrice?.setOnClickListener(this)
    }

    private fun setupSpinnerType() {
        val mListType = listOf("Type","Chocolate","Matcha","Strawberry","Cacao","Vani","Other","Mix")
        spnShoppingFilterByType?.attachDataSource(mListType)
        spnShoppingFilterByType?.setOnClickListener(this)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when(parent){
                spnShoppingFilterByType -> handleLogicFileterByType()
                spnShoppingFilterByPrice ->handleLogicFileterByPrice()
                spnShoppingFilterByDiscount ->handleLogicFileterByDiscount()
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
                R.id.imgShoppingBack ->{
                    this.findNavController().navigate(R.id.fragmentStore)
                }
                R.id.imgShoppingToCart->{
                    if (CommonUtils.instace.getOrderList()!!.size > 0){
                        this.findNavController().navigate(R.id.fragmentCart)
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
            handleGetListIceCream()
        }else {
            setupListIceCream(mListIceCream)
            mAdapter?.filter(query.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isEmpty()) {
            handleGetListIceCream()
        }
        return false
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                handleGetListIceCream()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}
