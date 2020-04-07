package com.example.vinid_icecreams.ui.fragment.user.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import com.example.vinid_icecreams.model.OrderInfor
import kotlinx.android.synthetic.main.fragment_order_history.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class HistoryFragment : BaseFragment<HistoryViewModel>(), View.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HistoryViewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(HistoryViewModel::class.java)
    }

    override fun providerViewModel(): HistoryViewModel = viewModel

    private val historyController: HistoryController by lazy {
        HistoryController(
            ::toDetailsHistory
        )
    }

    private var listOrderInfor =  ArrayList<OrderInfor>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_order_history, container, false)
    }

    override fun setUpUI() {
        super.setUpUI()
        handleEventClick()
        setupListOrder()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.listOrderInfor.observe(viewLifecycleOwner, Observer { data ->
            data.reverse()
            historyController.listOrderInfor = data
            listOrderInfor = data
        })
        handleGetListHistory()
    }

    private fun handleEventClick() {
        imgBackToUser.setOnClickListener(this)
    }

    private fun handleGetListHistory() {
        if (isConnectToNetwork(context)) {
            viewModel.getOrderUser()
        } else {
            showNoConnection()
        }
    }

    private fun setupListOrder() {
        rcvHistory.run {
            setItemSpacingDp(4)
            setController(historyController)
        }
    }

    private fun toDetailsHistory(position: Int){
        val bundle = bundleOf("ID" to listOrderInfor[position].id)
        this.findNavController().navigate(R.id.fragmentOrderDetails,bundle)
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0) {
                imgBackToUser -> {
                    findNavController().navigate(R.id.fragmentProfile)
                }
            }
        }
    }


}