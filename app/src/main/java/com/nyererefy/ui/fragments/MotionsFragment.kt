package com.nyererefy.ui.fragments

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyererefy.R
import com.nyererefy.adapters.MotionsAdapter
import com.nyererefy.utilities.common.ListItemClickListener
import com.nyererefy.viewmodels.MotionsViewModel

class MotionsFragment : androidx.fragment.app.Fragment(), ListItemClickListener, Function0<Unit> {
    override fun onRetryClick(view: View, position: Int) {
    }

    private lateinit var adapter: MotionsAdapter
    private lateinit var viewModel: MotionsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = MotionsAdapter(activity!!, this)
        viewModel = ViewModelProviders.of(this).get(MotionsViewModel::class.java)

        viewModel.motions.observe(this, Observer { adapter.submitList(it) })
        viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter
        return rootView
    }

    override fun invoke() {
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isResumed) {
            onResume()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!userVisibleHint) {
            return
        }
        activity!!.setTitle(R.string.title_motions)
    }

}