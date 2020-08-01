package com.example.vinid_icecreams.ui.fragment.home.shopping


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.ui.activity.home.HomeViewModel
import com.example.vinid_icecreams.ui.fragment.home.shopping.ShoppingController.Companion.ITEM_PER_ROW
import kotlinx.android.synthetic.main.fragment_shopping.*
import javax.inject.Inject

class ShoppingFragment : BaseFragment<ShoppingViewModel>(), AdapterView.OnItemSelectedListener
    , View.OnClickListener
    , SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: ShoppingViewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(ShoppingViewModel::class.java)
    }

    private val homeViewModel: HomeViewModel by lazy {
        ViewModelProviders.of(requireActivity(),viewModelFactory).get(HomeViewModel::class.java)
    }

    private var listIceCream : ArrayList<IceCream> = ArrayList()

    private var skeleton: SkeletonScreen? = null

    private val shoppingController: ShoppingController by lazy {
        ShoppingController(
            ::toDetailsIceCream
        )
    }

    override fun providerViewModel(): ShoppingViewModel = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping,container,false)
    }

    override fun setUpUI() {
        handleOnClick()
        setupBackDevice()
        setUpSpinnerFilter()
        setUpSearchView()
    }

    private fun setUpSearchView() {
        svShoppingIcecream.setOnQueryTextListener(this)
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.listIceCream.observe(viewLifecycleOwner, Observer { data ->
            listIceCream = data
            setupListIceCream(listIceCream)
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            toggleSkeletonVisibility(it.isLoading)
            swRefreshLayout.isRefreshing = it.isLoading
        })

        handleGetListIceCream()
    }

    private fun handleOnClick() {
        /*handle onclick*/
        imgShoppingBack.setOnClickListener(this)
        imgShoppingToCart.setOnClickListener(this)
        swRefreshLayout.setOnRefreshListener {
            handleGetListIceCream()
        }
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
        val id = homeViewModel.getStoreSelection()?.id
        if (id == null){
            showSomeThingWentWrong(activity)
            return
        }
        if (isConnectToNetwork(context)) {
            viewModel.getListIceCream(id)
        }else{
            showNoConnection()
        }
    }

    private fun setupListIceCream(data :ArrayList<IceCream>){
        shoppingController.listIceCream = data
        rcvShoppingIceCream.layoutManager =
            GridLayoutManager(context, ITEM_PER_ROW, RecyclerView.VERTICAL, false)
        rcvShoppingIceCream.setController(shoppingController)
        shoppingController.requestModelBuild()
    }


    private fun setupSpinnerDiscount() {
        val mListType = listOf("Discount","10%","20%","50%")
        spnShoppingFilterByDiscount?.attachDataSource(mListType)
        spnShoppingFilterByDiscount?.setOnClickListener(this)
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
                spnShoppingFilterByType -> handleLogicFilterByType()
                spnShoppingFilterByPrice ->handleLogicFilterByPrice()
                spnShoppingFilterByDiscount ->handleLogicFilterByDiscount()
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    private fun handleLogicFilterByDiscount() {
    }

    private fun handleLogicFilterByPrice() {
    }

    private fun handleLogicFilterByType() {
    }

    override fun onClick(v: View?) {
        if(v != null){
            when(v.id){
                R.id.imgShoppingBack ->{
                    this.findNavController().navigate(R.id.fragmentStore)
                }
                R.id.imgShoppingToCart->{
                    if (homeViewModel.getListOrder().size > 0){
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
            setupListIceCream(listIceCream)
            shoppingController.filter(query.toLowerCase())
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText == null || newText.isEmpty()) {
            handleGetListIceCream()
        }
        return false
    }

    private fun toDetailsIceCream(position :Int){
        val bundle = bundleOf("DETAILS" to listIceCream[position].id)
        findNavController().navigate(R.id.fragmentDetails,bundle)
    }

    companion object{
        var TAG = ShoppingFragment::class.java.name
    }

    private fun toggleSkeletonVisibility(isLoading: Boolean) {
        if (!isLoading) {
            skeleton?.hide()
            skeleton = null
            return
        }
        if (skeleton == null) {
            skeleton = Skeleton.bind(rcvShoppingIceCream)
                .adapter(rcvShoppingIceCream.adapter)
                .load(R.layout.raw_ice_cream_skeleton)
                .shimmer(true)
                .color(android.R.color.white)
                .angle(0)
                .show()
        } else {
            skeleton?.show()
        }
    }

    override fun onDestroyView() {
        skeleton = null
        rcvShoppingIceCream.adapter = null
        super.onDestroyView()
    }
}
