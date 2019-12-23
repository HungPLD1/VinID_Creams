package com.example.vinid_icecreams.view.fragment.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.Store
import com.example.vinid_icecreams.view.adapter.adapterStore.AdapterStore
import com.example.vinid_icecreams.view.adapter.adapterStore.OnItemStoreClicklistener
import kotlinx.android.synthetic.main.fragment_store.*

class FragmentStore : Fragment() ,OnItemStoreClicklistener {
    var mListStore : ArrayList<Store> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_store,container,false)
        setupView()
        return view
    }

    private fun setupView() {
        setupListStore()
    }

    private fun setupListStore() {
        val mAdapterStore = AdapterStore(context, mListStore, this )
        rcvStore.adapter = mAdapterStore
    }

    override fun onItemClick(positon: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}