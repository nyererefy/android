//package com.nyererefy.ui.fragments
//
//import android.graphics.PorterDuff
//import android.os.Bundle
//import android.view.MenuItem
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProviders
//import com.nyererefy.R
//import com.nyererefy.adapters.CategoriesAdapter
//import com.nyererefy.adapters.StatsAdapter
//import com.nyererefy.utilities.common.Constants
//import com.nyererefy.utilities.common.NetworkState
//import kotlinx.android.synthetic.main.election_view_activity.*
//import kotlinx.android.synthetic.main.election_view_content.*
//import org.jetbrains.anko.design.longSnackbar
//
//
//class ElectionView : AppCompatActivity() {
//    private lateinit var viewModel: CategoriesViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.election_view_activity)
//        setSupportActionBar(toolbar)
//
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        val drawable = toolbar.navigationIcon
//        drawable!!.setColorFilter(ContextCompat.getColor(this, R.color.accent_color), PorterDuff.Mode.SRC_ATOP)
//
//        showCategories()
//        swipeRefreshLayout.setOnRefreshListener { showCategories() }
//    }
//
//    private fun showCategories() {
//        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)
//
//        val data = intent.extras
//
//        var electionId = 0
//        var electionTitle = ""
//
//        if (data != null) {
//            electionId = data.getInt(Constants.ELECTION_ID)
////            electionTitle = data?.getString(Constants.ELECTION_TITLE)
//        }
//
//        title = electionTitle
//
//        viewModel.getCategories(electionId).observe(this, Observer {
//            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@ElectionView)
//            val adapter = CategoriesAdapter(this@ElectionView, it!!)
//            recyclerView.adapter = adapter
//        })
//
//        viewModel.getStats(electionId).observe(this, Observer {
//            val columns = 2
//            statsRecyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@ElectionView, columns)
//            val adapter = StatsAdapter(this@ElectionView, it!!)
//            statsRecyclerView.adapter = adapter
//        })
//
//        viewModel.networkState.observe(this, Observer {
//            when (it) {
//                NetworkState.LOADING -> showProgress(true)
//                NetworkState.LOADED -> showProgress(false)
//                else -> {
//                    showProgress(false)
//                    coordinatorLayout.longSnackbar(it?.msg.toString())
//                }
//            }
//        })
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> super.onBackPressed()
//        }
//        return true
//    }
//
//    private fun showProgress(boolean: Boolean) {
//        swipeRefreshLayout.isRefreshing = boolean
//    }
//}
