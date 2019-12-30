package com.example.vinid_icecreams.view.fragment.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.mock.MockData
import com.example.vinid_icecreams.model.Event
import com.example.vinid_icecreams.view.adapter.adapterNotification.AdapterNotification
import com.example.vinid_icecreams.view.adapter.adapterNotification.OnItemNotificationClicklistener

class FragmentEvent : Fragment(),OnItemNotificationClicklistener {
    private var rcvEvent : RecyclerView? = null
    private var mListEvent : ArrayList<Event>? = null
    private var mAdapter : AdapterNotification? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view  = layoutInflater.inflate(R.layout.fragment_event,container,false)
        initView(view)
        setupListEvent()
        return view
    }

    private fun setupListEvent() {
        mListEvent = MockData.getListEvent()
        mAdapter = AdapterNotification(context, mListEvent!!,this)
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
}