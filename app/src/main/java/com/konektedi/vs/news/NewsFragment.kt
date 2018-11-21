package com.konektedi.vs.news

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.ListItemClickListener

class NewsFragment : Fragment(), ListItemClickListener, Function0<Unit> {

    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        adapter = NewsAdapter(activity!!, this)
        viewModel = ViewModelProviders.of(this).get(NewsViewModel::class.java)

        viewModel.news.observe(this, Observer { adapter.submitList(it) })
        viewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment, container, false)
        val recyclerView = rootView.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter
        return rootView
    }

    override fun onRetryClick(view: View?, position: Int) {

    }

    override fun invoke() {

    }

}