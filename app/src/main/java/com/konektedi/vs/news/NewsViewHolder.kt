package com.konektedi.vs.news

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.common.getRandomColor
import com.konektedi.vs.utilities.models.Post

class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val timeView: TextView = view.findViewById(R.id.timeView)
    private val titleView: TextView = view.findViewById(R.id.titleView)
    private val nameView: TextView = view.findViewById(R.id.nameView)
    private val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)


    fun bind(post: Post?, mContext: Context) {
        titleView.text = post!!.title
        timeView.text = post.time
        nameView.text = post.name

        val color = getRandomColor(mContext)
        constraintLayout.setBackgroundColor(Color.parseColor(color))

        itemView.setOnClickListener {
            val intent = Intent(mContext, NewsView::class.java)
            intent.putExtra(Constants.POST_ID, post.postId)
            intent.putExtra(Constants.POST, post.post)
            intent.putExtra(Constants.TITLE, post.title)
            mContext.startActivity(intent)
        }
    }

    companion object {
        fun create(parent: ViewGroup): NewsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_post, parent, false)
            return NewsViewHolder(view)
        }
    }
}