package com.konektedi.vs.motions

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.MenuItem
import android.view.View
import com.konektedi.vs.R
import com.konektedi.vs.opinions.OpinionsAdapter
import com.konektedi.vs.opinions.OpinionsViewModel
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.NetworkState
import kotlinx.android.synthetic.main.activity_motion_view.*
import kotlinx.android.synthetic.main.content_motion_view.*
import kotlinx.android.synthetic.main.motion_not_participated_sub_content.*
import kotlinx.android.synthetic.main.motion_participated_sub_content.*
import org.jetbrains.anko.design.longSnackbar

class MotionViewActivity : AppCompatActivity(), Function0<Unit> {
    private lateinit var viewModel: MotionsViewModel
    private lateinit var oViewModel: OpinionsViewModel
    private var motionId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_view)
        setSupportActionBar(toolbar)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProviders.of(this).get(MotionsViewModel::class.java)
        oViewModel = ViewModelProviders.of(this).get(OpinionsViewModel::class.java)

        showMotion()
    }

    private fun showMotion() {
        val data = intent.extras!!

        motionId = data.getInt(Constants.MOTION_ID)
        viewModel.getMotion(motionId).observe(this, Observer {
            title = it!!.title
            title_view.text = it.title
            motion_view.text = it.motion

            //Showing it after loading data to avoid stupid layout coming front.
            viewSwitcher.visibility = View.VISIBLE
            if (it.participated == 1) {
                getOpinions()
                if (viewSwitcher.currentView != participated_layout) viewSwitcher.showNext()
            } else {
                submitBtn.setOnClickListener { submitOpinion(motionId) }
                if (viewSwitcher.currentView != not_participated_layout) viewSwitcher.showPrevious()
            }
        })

        viewModel.repoNetworkState.observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> progressBar.visibility = View.VISIBLE
                NetworkState.LOADED -> progressBar.visibility = View.GONE
                else -> {
                    progressBar.visibility = View.GONE
                    coordinatorLayout.longSnackbar(it!!.msg.toString())
                }
            }
        })

    }

    private fun getOpinions() {
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)

        val adapter = OpinionsAdapter(this, this)

        oViewModel.getOpinions(motionId).observe(this, Observer { adapter.submitList(it) })

        oViewModel.networkState.observe(this, Observer { adapter.setNetworkState(it) })

        recyclerView.addItemDecoration(androidx.recyclerview.widget.DividerItemDecoration(this,
                androidx.recyclerview.widget.DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
    }

    private fun submitOpinion(motion_id: Int) {
        var vote = ""

        val selectedId = radioGroup.checkedRadioButtonId

        when (selectedId) {
            yesRadioButton.id -> vote = "yes"
            noRadioButton.id -> vote = "no"
        }

        val map = mapOf(
                Constants.MOTION_ID to motion_id.toString(),
                Constants.OPINION to opinionInput.text.toString(),
                Constants.VOTE to vote
        )

        viewModel.submitOpinion(map).observe(this, Observer {
            when (it) {
                NetworkState.LOADING -> showProgressBar(true)
                NetworkState.LOADED -> {
                    //Showing all opinions
                    getOpinions()
                    if (viewSwitcher.currentView != participated_layout) viewSwitcher.showNext()
                    coordinatorLayout.longSnackbar(R.string.opinion_added)
                }
                else -> {
                    showProgressBar(false)
                    coordinatorLayout.longSnackbar(it!!.msg.toString())
                }
            }
        })
    }

    private fun showProgressBar(boolean: Boolean) =
            if (boolean) progressBar.visibility = View.VISIBLE else progressBar.visibility = View.GONE

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return true
    }

    override fun invoke() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
