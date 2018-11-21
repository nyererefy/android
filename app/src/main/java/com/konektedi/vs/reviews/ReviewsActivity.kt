package com.konektedi.vs.reviews

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.Constants.REVIEW
import com.konektedi.vs.utilities.common.ListItemClickListener
import com.konektedi.vs.utilities.common.NetworkState
import kotlinx.android.synthetic.main.activity_reviews.*
import kotlinx.android.synthetic.main.content_reviews.*
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

class ReviewsActivity : AppCompatActivity(),
        ListItemClickListener,
        Function0<Unit> {


    private lateinit var viewModel: ReviewsViewModel
    private lateinit var adapter: ResultsAdapter
    private lateinit var rAdapter: ReviewsAdapter
    private var categoryId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Init view model
        viewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java)

        val data = intent.extras
        categoryId = data.getInt(Constants.CATEGORY_ID)
        val category = data?.getString(Constants.CATEGORY)
        title = category

        getVotes(categoryId)
        getReviews(categoryId)

        swipeToRefresh.setOnRefreshListener {
            getVotes(categoryId)
            getReviews(categoryId)
        }

        review_input.addTextChangedListener(textWatcher)
        add_review_btn.setOnClickListener { submitReview(categoryId) }
    }

    private fun getVotes(categoryId: Int) {
        viewModel.getVotes(categoryId).observe(this, Observer { it ->
            it?.run {
                val columns = 2
                votes_recycler_view.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@ReviewsActivity, columns)
                adapter = ResultsAdapter(this@ReviewsActivity, it)
                votes_recycler_view.adapter = adapter
            }
        })

        viewModel.mNetworkState.observe(this, Observer {
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

    private fun getReviews(categoryId: Int) {
        rAdapter = ReviewsAdapter(this, this)
        reviews_recycler_view.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        reviews_recycler_view.adapter = rAdapter

        viewModel.getReviews(categoryId).observe(this, Observer { rAdapter.submitList(it) })
        viewModel.pagedNetworkState.observe(this, Observer { rAdapter.setNetworkState(it) })
    }

    private fun showProgress(bool: Boolean) {
        swipeToRefresh.isRefreshing = bool
    }

    private fun submitReview(categoryId: Int) {
        val map = mapOf(
                Constants.CATEGORY_ID to categoryId.toString(),
                REVIEW to review_input.text.toString()
        )

        viewModel.submitReview(map).observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> {
                    review_input.isEnabled = false
                    showSubmitProgress(true)
                }
                NetworkState.LOADED -> {
                    review_input.setText("")
                    showSubmitProgress(false)
                    coordinatorLayout.longSnackbar(getString(R.string.review_added))
                }
                else -> {
                    review_input.isEnabled = true
                    coordinatorLayout.snackbar(it?.msg.toString())
                    showSubmitProgress(false)
                }
            }
        })
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            add_review_btn.visibility = View.INVISIBLE
        }

        override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
            add_review_btn.visibility = View.VISIBLE
        }

        override fun afterTextChanged(editable: Editable) {
            if (review_input.text.toString().isEmpty()) add_review_btn.visibility = View.INVISIBLE
            else add_review_btn.visibility = View.VISIBLE
        }
    }

    private fun showSubmitProgress(bool: Boolean) = when {
        bool -> {
            submit_progress_bar.visibility = View.VISIBLE
            add_review_btn.visibility = View.INVISIBLE
        }
        else -> {
            submit_progress_bar.visibility = View.INVISIBLE
            add_review_btn.visibility = View.VISIBLE
        }
    }

    override fun invoke() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.reviews_activity_menu, menu)
        return true
    }

    //Todo find a way to get to categories.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
            R.id.refresh -> swipeToRefresh.setOnRefreshListener {
                getReviews(categoryId)
                getVotes(categoryId)
            }
        }
        return true
    }

    override fun onRetryClick(view: View, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}
