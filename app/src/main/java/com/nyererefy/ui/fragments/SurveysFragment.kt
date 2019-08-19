package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nyererefy.R
import com.nyererefy.di.Injectable
import com.nyererefy.utilities.common.ListItemClickListener

class SurveysFragment : Fragment(), ListItemClickListener, Function0<Unit>, Injectable {
    override fun onRetryClick(view: View, position: Int) {
    }

//    private lateinit var adapter: MotionsAdapter
//    private lateinit var viewModel: MotionsViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        adapter = MotionsAdapter(activity!!, this)
//        viewModel = ViewModelProviders.of(this).get(MotionsViewModel::class.java)
//
//        viewModel.motions.observe(this, Observer { adapter.submitList(it) })
//        viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.elections_fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.activity)
//        recyclerView.adapter = adapter
        return rootView
    }

    override fun invoke() {
    }

}