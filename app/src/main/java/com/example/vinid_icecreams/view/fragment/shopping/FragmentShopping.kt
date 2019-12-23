package com.example.vinid_icecreams.view.fragment.shopping


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.vinid_icecreams.R
import kotlinx.android.synthetic.main.fragment_shopping.*


class FragmentShopping : Fragment(), AdapterView.OnItemSelectedListener {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.fragment_shopping,container,false)
        iniView()
        return mView
    }

    private fun iniView() {
        setUpSpinerFilter()
    }

    /*Set up and add data spiner filter*/
    private fun setUpSpinerFilter() {
        setupSpinerType()
        setupSpinerPrice()
        setupSpinerDiscount()
    }

    private fun setupSpinerDiscount() {
        val mListType = arrayOf("Filter by discount","10%","20%","50%")
        val mAdapterDiscount = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            mListType // Array
        )
        spinerFilterByType.adapter = mAdapterDiscount
        spinerFilterByType.setSelection(0)
        spinerFilterByType.onItemSelectedListener = this
    }

    private fun setupSpinerPrice() {
        val mListType = arrayOf("Filter by price","0 - 100K","100K - 200K","200K - 300K","300 - 400K","400K - 500K","> 500K")
        val mAdapterPrice = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            mListType // Array
        )
        spinerFilterByType.adapter = mAdapterPrice
        spinerFilterByType.setSelection(0)
        spinerFilterByType.onItemSelectedListener = this
    }

    private fun setupSpinerType() {
        val mListType = arrayOf("Filter by type","Chocolate","Matcha","Strawberry","Cacao","Vani","Other","Mix")
        val mAdapterType = ArrayAdapter(
            this, // Context
            android.R.layout.simple_spinner_item, // Layout
            mListType // Array
        )
        spinerFilterByType.adapter = mAdapterType
        spinerFilterByType.setSelection(0)
        spinerFilterByType.onItemSelectedListener = this
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun handleLogicFileterByPrice() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun handleLogicFileterByType() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
