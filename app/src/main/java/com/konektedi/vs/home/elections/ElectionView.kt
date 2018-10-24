package com.konektedi.vs.home.elections

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.home.categories.CategoriesAdapter
import com.konektedi.vs.home.categories.CategoriesViewModel
import com.konektedi.vs.home.results.ResultsView
import com.konektedi.vs.home.reviews.ReviewsActivity
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import kotlinx.android.synthetic.main.election_view_activity.*
import kotlinx.android.synthetic.main.election_view_content.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast

class ElectionView : AppCompatActivity() {
    private lateinit var viewModel: CategoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.election_view_activity)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initListeners()
        showCategories()

    }

    private fun initListeners() {
        resultsViewBtn.setOnClickListener {
            val intent = Intent(this@ElectionView, ResultsView::class.java)
            startActivity(intent)
        }

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
                NetworkState.LOADING -> showProgressBar()
                NetworkState.LOADED -> hideProgressBar()
                NetworkState.END -> {
                    longToast(R.string.no_category)
                    hideProgressBar()
                }
                else -> {
                    hideProgressBar()
                    longToast(R.string.error_occurred)
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

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }
}
