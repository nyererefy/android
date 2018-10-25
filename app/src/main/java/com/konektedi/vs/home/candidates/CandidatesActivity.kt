package com.konektedi.vs.home.candidates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants.CATEGORY
import com.konektedi.vs.utilities.common.Constants.CATEGORY_ID
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.common.NetworkState
import kotlinx.android.synthetic.main.candidates_activity.*
import kotlinx.android.synthetic.main.candidates_content.*

class CandidatesActivity : AppCompatActivity() {
    private lateinit var adapter: CandidatesAdapter
    private lateinit var viewModel: CandidatesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidates_activity)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        getCandidates()
    }

    private fun getCandidates() {
        val data = intent.extras

        val electionId = data.getInt(ELECTION_ID)
        val categoryId = data.getInt(CATEGORY_ID)
        val category = data?.getString(CATEGORY)
        title = category

        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        viewModel.networkState.observe(this, Observer { it ->
            when (it) {
                NetworkState.LOADING -> showProgressBar()
                NetworkState.LOADED -> hideProgressBar()
                NetworkState.END -> {
                    hideProgressBar()
                    showAlert(getString(R.string.no_candidates))
                }
                NetworkState.ERROR -> {
                    hideProgressBar()
                    showAlert(getString(R.string.error))
                }
                NetworkState.FAILED -> {
                    hideProgressBar()
                    showAlert(getString(R.string.failed_connect))
                }
            }
        })

        viewModel.getCandidates(electionId, categoryId)?.observe(this,
                Observer { candidatesList ->
                    candidatesList?.run {
                        val numberOfColumns = 2
                        recyclerView.layoutManager = GridLayoutManager(this@CandidatesActivity, numberOfColumns)
                        adapter = CandidatesAdapter(this@CandidatesActivity, candidatesList)
                        recyclerView.adapter = adapter

                        //TODO Use only this btn and radioButtons for selecting candidate.
                    }
                })
    }

    fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun showAlert(alertText: String) {
        cardView.visibility = View.VISIBLE
        error_msg.setHtml(alertText)
    }

    fun showError(alertText: Int) {
        cardView.visibility = View.VISIBLE
        error_msg.setHtml(getString(alertText))
    }

    fun finishVote() {
        super.onBackPressed()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }
}
