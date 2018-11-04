package com.konektedi.vs.candidates

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.reviews.ReviewsActivity
import com.konektedi.vs.student.grabPreference
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.Constants.CATEGORY
import com.konektedi.vs.utilities.common.Constants.CATEGORY_ID
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.common.NetworkState
import com.konektedi.vs.utilities.models.Candidate
import kotlinx.android.synthetic.main.candidates_activity.*
import kotlinx.android.synthetic.main.candidates_content.*
import org.jetbrains.anko.*
import java.util.*

class CandidatesActivity : AppCompatActivity() {
    private lateinit var adapter: CandidatesAdapter
    private lateinit var viewModel: CandidatesViewModel
    private lateinit var category: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.candidates_activity)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        getCandidates()
    }

    private fun getCandidates() {
        val data = intent.extras

        val electionId = data.getInt(ELECTION_ID)
        val categoryId = data.getInt(CATEGORY_ID)

        Log.d("categoryId", categoryId.toString())
        Log.d("electionId", electionId.toString())

        category = data?.getString(CATEGORY)!!
        title = category


        viewModel.networkState.observe(this, Observer { it ->
            when (it) {
                NetworkState.LOADING -> showProgressBar()
                NetworkState.LOADED -> hideProgressBar()
                else -> {
                    hideProgressBar()
                    showAlert(it?.msg)
                }
            }
        })

        viewModel.getCandidates(electionId, categoryId)?.observe(this,
                Observer {
                    it?.run {
                        val columns = 2
                        recyclerView.layoutManager = GridLayoutManager(this@CandidatesActivity, columns)
                        adapter = CandidatesAdapter(this@CandidatesActivity, it)
                        recyclerView.adapter = adapter
                        //TODO Use only this btn and radioButtons for selecting candidate.
                    }
                })
    }

    fun confirmVoting(candidate: Candidate) {
        val name = candidate.name

        alert("You are about to vote for $name. Do you really want to proceed?",
                "Confirm action!") {
            yesButton {
                voteForCandidate(candidate)
            }
            noButton { toast("Action cancelled") }
            isCancelable = false
        }.show()
    }

    private fun voteForCandidate(candidate: Candidate) {
        val map = HashMap<String, String>()

        map[ELECTION_ID] = candidate.electionId
        map[CATEGORY_ID] = candidate.categoryId
        map[Constants.CANDIDATE_ID] = candidate.candidateId
        map[Constants.DEVICE] = deviceName

        viewModel.submitVote(map).observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> showProgressBar()
                NetworkState.LOADED -> {
                    hideProgressBar()
                    onSuccessfulVote(candidate)
                }
                else -> {
                    hideProgressBar()
                    showAlert(it?.msg)
                }
            }
        })
    }

    private fun onSuccessfulVote(candidate: Candidate) {
        val candidateName = candidate.name
        val username = grabPreference(this, Constants.USERNAME)

        alert("You have successfully voted for $candidateName.",
                "Thanks $username!") {
            yesButton {
                startActivity<ReviewsActivity>(
                        ELECTION_ID to candidate.electionId,
                        CATEGORY_ID to candidate.categoryId
                )
            }
            noButton {}
            isCancelable = false
        }.show()
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun showAlert(alertText: String?) {
        alertText?.run {
            cardView.visibility = View.VISIBLE
            error_msg.setHtml(alertText)
        }
    }

    fun showError(alertText: Int) {
        cardView.visibility = View.VISIBLE
        error_msg.setHtml(getString(alertText))
    }

    fun passCategoryName() = category

    private val deviceName: String
        get() = (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }
}
