package com.konektedi.vs.reviews

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.candidates.Profile
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.models.Result

class ResultViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    private val nameView: TextView = view.findViewById(R.id.nameView)
    private val resultView: TextView = view.findViewById(R.id.result_view)
    private val cover: ImageView = view.findViewById(R.id.cover)

    fun bind(candidate: Result?, mContext: Context) {
        nameView.text = candidate!!.name
        resultView.text = candidate.votes.toString()

        Glide.with(mContext)
                .load(candidate.cover)
                .apply(RequestOptions()
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover)

        nameView.setOnClickListener { showProfile(candidate, mContext) }
    }

    private fun showProfile(candidate: Result, mContext: Context) {
        val intent = Intent(mContext, Profile::class.java)

        intent.putExtra(Constants.COVER, candidate.cover)
        intent.putExtra(Constants.NAME, candidate.name)

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    }


    companion object {
        fun create(parent: ViewGroup): ResultViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_result, parent, false)
            return ResultViewHolder(view)
        }
    }
}