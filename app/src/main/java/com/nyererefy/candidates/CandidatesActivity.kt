package com.nyererefy.candidates

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nyererefy.R
import com.nyererefy.student.grabPreference
import com.nyererefy.utilities.common.Constants
import com.nyererefy.utilities.common.Constants.CATEGORY
import com.nyererefy.utilities.common.Constants.CATEGORY_ID
import com.nyererefy.utilities.common.Constants.ELECTION_ID
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.models.Candidate
import kotlinx.android.synthetic.main.candidates_activity.*
import kotlinx.android.synthetic.main.candidates_content.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.noButton
import org.jetbrains.anko.toast
import org.jetbrains.anko.yesButton
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
        val drawable = toolbar.navigationIcon
        drawable!!.setColorFilter(ContextCompat.getColor(this, R.color.accent_color), PorterDuff.Mode.SRC_ATOP)

        viewModel = ViewModelProviders.of(this).get(CandidatesViewModel::class.java)

        swipeRefreshLayout.setOnRefreshListener { getCandidates() }
        getCandidates()
    }

    private fun getCandidates() {
        val data = intent.extras

        val electionId = data?.getInt(ELECTION_ID)
        val categoryId = data?.getInt(CATEGORY_ID)

        category = data?.getString(CATEGORY)!!
        title = category


        viewModel.networkState.observe(this, Observer { it ->
            when (it) {
                NetworkState.LOADING -> progress(true)
                NetworkState.LOADED -> progress(false)
                else -> {
                    progress(false)
                    showAlert(it?.msg)
                }
            }
        })

//        viewModel.getCandidates(electionId, categoryId).observe(this,
//                Observer {
//                    it?.run {
//                        val columns = 2
//                        recyclerView.layoutManager = androidx.recyclerview.widget.GridLayoutManager(this@CandidatesActivity, columns)
//                        adapter = CandidatesAdapter(this@CandidatesActivity, it.candidates)
//                        recyclerView.adapter = adapter
//
//                        if (it.category.isOpened == 1) {
//                            if (it.category.hasVoted == 0) {
//                                vote_btn.visibility = View.VISIBLE
//                                vote_btn.setOnClickListener { confirmVoting(adapter.passSelectedCandidate()) }
//                            } else showError(R.string.voted)
//                        } else showError(R.string.voting_disabled)
//                    }
//                })
    }

    private fun confirmVoting(candidate: Candidate?) {
        if (candidate == null) {
            coordinatorLayout.longSnackbar("Select Candidate!")
        } else {
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
    }

    private fun voteForCandidate(candidate: Candidate) {
        val map = HashMap<String, String>()

        map[ELECTION_ID] = candidate.electionId.toString()
        map[CATEGORY_ID] = candidate.categoryId.toString()
        map[Constants.CANDIDATE_ID] = candidate.candidateId.toString()

        viewModel.submitVote(map).observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> progress(true)
                NetworkState.LOADED -> {
                    progress(false)
                    onSuccessfulVote(candidate)
                }
                else -> {
                    progress(false)
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
            noButton {}
            isCancelable = true
        }.show()
    }

    private fun progress(boolean: Boolean) {
        swipeRefreshLayout.isRefreshing = boolean
    }

    private fun showAlert(alertText: String?) {
        alertText?.run {
            cardView.visibility = View.VISIBLE
            error_msg.setHtml(alertText)
        }
    }

    private fun showError(alertText: Int) {
        cardView.visibility = View.VISIBLE
        error_msg.setHtml(getString(alertText))
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }
}
