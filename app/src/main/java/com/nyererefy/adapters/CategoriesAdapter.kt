package com.nyererefy.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.nyererefy.R
import com.nyererefy.utilities.common.getRandomColor
import com.nyererefy.utilities.models.Category

/**
 * Created by Sy on b/14/2018.
 */

class CategoriesAdapter(private val mContext: Context,
                        private val list: List<Category>) :
        androidx.recyclerview.widget.RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {

    inner class CategoriesViewHolder(view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
        private val categoryView: TextView = view.findViewById(R.id.categoryView)
        private val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)

        fun bind(candidate: Category?, mContext: Context) {
            categoryView.text = candidate!!.category

            val color = getRandomColor(mContext)
            constraintLayout.setBackgroundColor(Color.parseColor(color))

//            itemView.setOnClickListener {
//                //Checking if user has already voted for specific category.
//                val intent: Intent? = if (candidate.hasVoted == 0) {
//                    Intent(mContext, CandidatesActivity::class.java)
//
//                } else Intent(mContext, ReviewsActivity::class.java)
//
//                intent?.run {
//                    putExtra(Constants.ELECTION_ID, candidate.electionId)
//                    putExtra(Constants.CATEGORY_ID, candidate.categoryId)
//                    putExtra(Constants.CATEGORY, candidate.category)
//
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                }
//                mContext.startActivity(intent)
//            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.z_category, parent, false)

        return CategoriesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(list[position], mContext)
    }


}