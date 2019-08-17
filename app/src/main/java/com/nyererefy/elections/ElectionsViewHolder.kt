package com.nyererefy.elections

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.constraintlayout.widget.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.nyererefy.R
import com.nyererefy.utilities.common.Constants
import com.nyererefy.utilities.common.getRandomColor
import com.nyererefy.utilities.models.Election

class ElectionsViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {

    private val electionView: TextView = view.findViewById(R.id.title_view)
    private val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)

    fun bind(election: Election?, mContext: Context) {
        electionView.text = election!!.electionTitle

        val color = getRandomColor(mContext)
        constraintLayout.setBackgroundColor(Color.parseColor(color))

        itemView.setOnClickListener {
            val intent = Intent(mContext, ElectionView::class.java)
            intent.putExtra(Constants.ELECTION_ID, election.electionId)
            intent.putExtra(Constants.ELECTION_TITLE, election.electionTitle)
            mContext.startActivity(intent)
        }
    }


    companion object {
        fun create(parent: ViewGroup): ElectionsViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.z_election, parent, false)
            return ElectionsViewHolder(view)
        }
    }
}