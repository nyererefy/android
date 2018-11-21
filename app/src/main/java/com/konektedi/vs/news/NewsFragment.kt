package com.konektedi.vs.news

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.ListItemClickListener

class NewsFragment : androidx.fragment.app.Fragment(), ListItemClickListener, Function0<Unit> {
    override fun onRetryClick(view: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

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
        val recyclerView = rootView.findViewById(R.id.recyclerView) as androidx.recyclerview.widget.RecyclerView
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this.activity)
        recyclerView.adapter = adapter
        return rootView
    }


    override fun invoke() {

    }

}