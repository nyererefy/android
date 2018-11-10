package com.konektedi.vs.elections

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.categories.CategoriesAdapter
import com.konektedi.vs.categories.CategoriesViewModel
import com.konektedi.vs.reviews.ReviewsActivity
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import kotlinx.android.synthetic.main.election_view_activity.*
import kotlinx.android.synthetic.main.election_view_content.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.longToast

class ElectionView : AppCompatActivity() {
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.election_view_activity)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initListeners()
        showCategories()
        swipeRefreshLayout.setOnRefreshListener { showCategories() }
    }

    private fun initListeners() {
        reviewsViewBtn.setOnClickListener {
            val intent = Intent(this@ElectionView, ReviewsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showCategories() {
        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel::class.java)

        val data = intent.extras

        var electionId = 0
        var electionTitle = ""

        if (data != null) {
            electionId = data.getInt(Constants.ELECTION_ID)
            electionTitle = data.getString(Constants.ELECTION_TITLE)
        }

        title = electionTitle

        viewModel.getCategories(electionId).observe(this, Observer { categories ->
            val adapter = CategoriesAdapter(this@ElectionView, categories)
            categoriesGridView.adapter = adapter
        })

        viewModel.networkState.observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> showProgress(true)
                NetworkState.LOADED -> showProgress(false)
                else -> {
                    showProgress(false)
                    coordinatorLayout.longSnackbar(it?.msg.toString())
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }

    private fun showProgress(boolean: Boolean) {
        swipeRefreshLayout.isRefreshing = boolean
    }
}
