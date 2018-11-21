package com.konektedi.vs.motions

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants
import com.konektedi.vs.utilities.models.Motion

class MotionsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val titleView: TextView = view.findViewById(R.id.title)

    fun bind(motion: Motion?, mContext: Context) {
        titleView.text = motion!!.title

        titleView.setOnClickListener {
            val intent = Intent(mContext, MotionViewActivity::class.java)
            intent.putExtra(Constants.MOTION_ID, motion.motionId)
            mContext.startActivity(intent)
        }
    }

    companion object {
        fun create(parent: ViewGroup): MotionsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_motion, parent, false)
            return MotionsViewHolder(view)
        }
    }
}