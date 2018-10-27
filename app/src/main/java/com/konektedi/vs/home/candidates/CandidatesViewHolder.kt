package com.konektedi.vs.home.candidates

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.student.grabPreference
import com.konektedi.vs.utilities.api.ApiN
import com.konektedi.vs.utilities.api.ApiUtilities
import com.konektedi.vs.utilities.api.getError
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.Constants.CANDIDATE_ID
import com.konektedi.vs.utilities.common.Constants.CATEGORY_ID
import com.konektedi.vs.utilities.common.Constants.DEVICE
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.models.Candidate
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class CandidatesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val nameView: TextView = view.findViewById(R.id.nameView)
    private val schoolView: TextView = view.findViewById(R.id.schoolView)
    private val voteBtn: Button = view.findViewById(R.id.voteBtn)
    private val cover: ImageView = view.findViewById(R.id.cover)

    fun bind(candidate: Candidate?, mContext: Context) {
        nameView.text = candidate!!.name
        schoolView.text = candidate.abbr

        Glide.with(mContext)
                .load(candidate.cover)
                .apply(RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover)

        if (candidate.participated == 1) {
            voteBtn.visibility = View.GONE
            (mContext as CandidatesActivity).showError(R.string.voted)

        } else {
            voteBtn.setOnClickListener { confirm(candidate, mContext) }
        }

        if (candidate.opened == 0) {
            voteBtn.visibility = View.GONE
            (mContext as CandidatesActivity).showError(R.string.voting_disabled)
        }

        nameView.setOnClickListener { showProfile(candidate, mContext) }
        cover.setOnClickListener { showProfile(candidate, mContext) }
        voteBtn.setOnClickListener { confirm(candidate, mContext) }
    }

    private fun showProfile(candidate: Candidate, mContext: Context) {
        val intent = Intent(mContext, Profile::class.java)

        intent.putExtra(Constants.COVER, candidate.cover)
        intent.putExtra(Constants.NAME, candidate.name)
        intent.putExtra(Constants.CLASS_NAME, candidate.abbr)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }

    private fun confirm(candidate: Candidate, mContext: Context) {

        val name = candidate.name

        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Confirm action!")
        builder.setMessage("You are about to vote for $name. Do you really want to proceed?")
        builder.setCancelable(false)
        builder.setPositiveButton("Yes! Vote now") { _, _ -> submitVote(candidate, mContext) }

        builder.setNegativeButton("No") { _, _ -> Toast.makeText(mContext, "Action cancelled", Toast.LENGTH_SHORT).show() }

        builder.show()
    }

    private fun submitVote(candidate: Candidate, mContext: Context) {

        (mContext as CandidatesActivity).showProgressBar()

        val map = HashMap<String, String>()

        map[ELECTION_ID] = candidate.electionId
        map[CATEGORY_ID] = candidate.categoryId
        map[CANDIDATE_ID] = candidate.candidateId
        map[DEVICE] = deviceName

        val call = ApiN.create().vote(map)

        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                mContext.hideProgressBar()
                when {
                    response.isSuccessful -> {
                        onSuccessfulVote(candidate, mContext)
                    }
                    else -> {
                        val error = getError(response)!!
                        mContext.showAlert(error)
                    }
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mContext.hideProgressBar()
                Toast.makeText(mContext, R.string.error_occurred, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onSuccessfulVote(candidate: Candidate, mContext: Context) {

        val candidateName = candidate.name
        val username = grabPreference(mContext, Constants.USERNAME)

        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Thanks $username!")
        builder.setMessage("You have successfully voted for $candidateName.")
        builder.setCancelable(false)

        builder.setNeutralButton("Close") { _, _ -> (mContext as CandidatesActivity).finishVote() }

        builder.show()
    }

    private val deviceName: String
        get() = (Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES::class.java.fields[Build.VERSION.SDK_INT].name)


    companion object {
        fun create(parent: ViewGroup): CandidatesViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_candidate, parent, false)
            return CandidatesViewHolder(view)
        }
    }
}