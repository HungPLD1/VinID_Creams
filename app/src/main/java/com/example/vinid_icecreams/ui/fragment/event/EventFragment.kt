package com.example.vinid_icecreams.ui.fragment.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.base.fragment.BaseFragment
import com.example.vinid_icecreams.di.viewModelModule.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_event.*
import javax.inject.Inject

class EventFragment : BaseFragment<EventViewModel>() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: EventViewModel by lazy {
        ViewModelProviders.of(this,viewModelFactory).get(EventViewModel::class.java)
    }

    override fun providerViewModel(): EventViewModel = viewModel

    private val eventController: EventController by lazy {
        EventController(
            ::onEventClick
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = layoutInflater.inflate(R.layout.fragment_event,container,false)

    override fun setUpUI() {
        super.setUpUI()
        setupRecycleView()
    }

    override fun setupViewModel() {
        super.setupViewModel()
        viewModel.listEvent.observe(viewLifecycleOwner, Observer { data ->
            eventController.listEvent = data
        })

        if (isConnectToNetwork(context)) {
            viewModel.getNotification()
        }else{
            showNoConnection()
        }
    }


    private fun setupRecycleView() {
        rcvEvent.run {
            setItemSpacingDp(4)
            setController(eventController)
        }
    }

    private fun onEventClick(position: Int){
       Toast.makeText(context,"Chỗ này chưa click được",Toast.LENGTH_SHORT).show()
   }


}