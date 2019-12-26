package com.example.vinid_icecreams.view.fragment.shopping


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vinid_icecreams.R
import com.example.vinid_icecreams.model.IceCream
import com.example.vinid_icecreams.view.adapter.adapterIceCream.AdapterIceCream
import com.example.vinid_icecreams.view.adapter.adapterIceCream.OnItemIceCreamClicklistener
import com.example.vinid_icecreams.view.fragment.details.FragmentDetails


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener,OnItemIceCreamClicklistener{
    var rcvIceCream : RecyclerView? = null
    var mListIceCream : ArrayList<IceCream> = ArrayList()
    var spinerFilterByType : Spinner?= null
    var spinerFilterByPrice : Spinner?= null
    var spinerFilterByDiscount : Spinner?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_shopping,container,false)
        iniView(mView)
        setUpSpinerFilter()
        return mView
    }

    private fun iniView(mView: View) {
        rcvIceCream = mView.findViewById(R.id.rcvIceCream)
        spinerFilterByType = mView.findViewById(R.id.spinerFilterByType)
        spinerFilterByPrice = mView.findViewById(R.id.spinerFilterByPrice)
        spinerFilterByDiscount = mView.findViewById(R.id.spinerFilterByDiscount)
    }

    /*Set up and add data spiner filter*/
    private fun setUpSpinerFilter() {
        setupSpinerType()
        setupSpinerPrice()
        setupSpinerDiscount()
        setupListIcream()
    }

    private fun setupListIcream() {
        val bundle = arguments
        val mData = bundle?.getSerializable("DATA")
        Log.d("Hungpld",mData.toString())
          mListIceCream.addAll(mData as ArrayList<IceCream>)
        val mAdapter = AdapterIceCream(context,mListIceCream,this)
        rcvIceCream?.layoutManager = GridLayoutManager(context,2)
        rcvIceCream?.adapter = mAdapter
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
        spinerFilterByDiscount?.adapter = mAdapterDiscount
        spinerFilterByDiscount?.setSelection(0)
        spinerFilterByDiscount?.onItemSelectedListener = this
    }


    override fun onItemClick(positon: Int) {
        val fragmentDetails = FragmentDetails()
        fragmentManager?.beginTransaction()?.replace(R.id.containerHome,fragmentDetails)?.addToBackStack(null)?.commit()
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
        spinerFilterByPrice?.adapter = mAdapterPrice
        spinerFilterByPrice?.setSelection(0)
        spinerFilterByPrice?.onItemSelectedListener = this
    }

    private fun setupSpinerType() {
        val mListType = arrayOf("Type","Chocolate","Matcha","Strawberry","Cacao","Vani","Other","Mix")
        val mAdapterType = context?.let {
            ArrayAdapter(
                it, // Context
                android.R.layout.simple_spinner_item, // Layout
                mListType // Array
            )
        }
        spinerFilterByType?.adapter = mAdapterType
        spinerFilterByType?.setSelection(0)
        spinerFilterByType?.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (parent != null) {
            when(parent){
                spinerFilterByType -> handleLogicFileterByType()
                spinerFilterByPrice ->handleLogicFileterByPrice()
                spinerFilterByDiscount ->handleLogicFileterByDiscount()
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


}
