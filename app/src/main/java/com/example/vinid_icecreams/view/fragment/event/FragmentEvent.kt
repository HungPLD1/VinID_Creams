package com.example.vinid_icecreams.view.fragment.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.kalert.KAlertDialog
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.utils.CommonUtils
import com.example.vinid_icecreams.utils.ProgressLoading
import com.example.vinid_icecreams.view.adapter.adapterNotification.AdapterNotification
import com.example.vinid_icecreams.view.adapter.adapterNotification.OnItemNotificationClicklistener
import com.example.vinid_icecreams.viewmodel.ViewModelIceCream

class FragmentEvent : Fragment(),OnItemNotificationClicklistener {
    private var rcvEvent : RecyclerView? = null
    private var mListEvent : ArrayList<Event>? = null
    private var mAdapter : AdapterNotification? = null
    private val mViewModel: ViewModelIceCream by lazy {
        ViewModelProviders.of(this).get(ViewModelIceCream::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_event,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        observeData()
        handleGetListEvent()
    }

    private fun observeData(){
            mViewModel.mListEvent.observe(this, Observer { data ->
                mListEvent = data
                setupListEvent(mListEvent)
                ProgressLoading.dismiss()
            })
    }

    private fun handleGetListEvent(){
        if (CommonUtils.instace.isConnectToNetwork(context)) {
            ProgressLoading.show(context)
            mViewModel.getNotification()
        }else{
            showNoConnection()
        }
    }

    private fun setupListEvent(mListEvent : ArrayList<Event>?) {
        mAdapter = mListEvent?.let { AdapterNotification(context, it,this) }
        rcvEvent?.layoutManager = LinearLayoutManager(context)
        rcvEvent?.adapter = mAdapter
    }

    private fun initView(view: View?) {
        rcvEvent = view?.findViewById(R.id.rcv_event)
        mListEvent = ArrayList()
    }

    override fun onItemClick(positon: Int) {
        Toast.makeText(context,"Chỗ này chưa click được",Toast.LENGTH_SHORT).show()
    }

    private fun showNoConnection(){
        val dialog = KAlertDialog(activity, KAlertDialog.ERROR_TYPE)
            .setTitleText("Missing connection ")
            .setContentText("Check your connection")
            .setConfirmClickListener{
                it.dismiss()
                handleGetListEvent()
            }
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }
}