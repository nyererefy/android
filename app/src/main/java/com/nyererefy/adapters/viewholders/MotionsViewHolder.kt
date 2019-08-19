//package com.nyererefy.adapters.viewholders
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.Color
//import androidx.constraintlayout.widget.ConstraintLayout
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import com.nyererefy.R
//import com.nyererefy.ui.fragments.MotionViewActivity
//import com.nyererefy.utilities.common.Constants
//import com.nyererefy.utilities.common.getRandomColor
//import com.nyererefy.utilities.models.Motion
//
//class MotionsViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
//
//    private val titleView: TextView = view.findViewById(R.id.title)
//    private val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
//
//
//    fun bind(motion: Motion?, mContext: Context) {
//        titleView.text = motion!!.title
//
//        val color = getRandomColor(mContext)
//        constraintLayout.setBackgroundColor(Color.parseColor(color))
//
//        itemView.setOnClickListener {
//            val intent = Intent(mContext, MotionViewActivity::class.java)
//            intent.putExtra(Constants.MOTION_ID, motion.motionId)
//            mContext.startActivity(intent)
//        }
//    }
//
//    companion object {
//        fun create(parent: ViewGroup): MotionsViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                    .inflate(R.layout.z_motion, parent, false)
//            return MotionsViewHolder(view)
//        }
//    }
//}