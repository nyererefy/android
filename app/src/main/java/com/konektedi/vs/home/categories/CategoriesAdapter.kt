package com.konektedi.vs.home.categories

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.konektedi.vs.R
import com.konektedi.vs.home.candidates.CandidatesActivity
import com.konektedi.vs.home.reviews.ReviewsActivity
import com.konektedi.vs.utilities.common.Constants.CATEGORY
import com.konektedi.vs.utilities.common.Constants.CATEGORY_ID
import com.konektedi.vs.utilities.common.Constants.ELECTION_ID
import com.konektedi.vs.utilities.common.Constants.REQUEST_FOR_ACTIVITY_CODE
import com.konektedi.vs.utilities.models.Category


class CategoriesAdapter// Gets the context so it can be used later
(private val mContext: Context, private val list: List<Category>?) : BaseAdapter() {

    // Total number of things contained within the adapter
    override fun getCount(): Int {
        return list?.size ?: 0
    }

    // Require for structure, not really used in my code.
    override fun getItem(position: Int): Any? {
        return null
    }

    // Require for structure, not really used in my code. Can
    // be used to get the id of an item in the adapter for
    // manual control.
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int,
                         convertView: View?, parent: ViewGroup): View {
        val btn: Button = if (convertView == null) {
            // if it's not recycled, initialize some attributes
            Button(mContext)
            //            btn.setLayoutParams(new GridView.LayoutParams(100, 55));
            //            btn.setPadding(8, 8, 8, 8);
        } else {
            convertView as Button
        }

        btn.text = list!![position].category

        // filenames is an array of strings
        btn.setTextColor(Color.parseColor("#FFFFFF"))
        btn.setBackgroundResource(R.drawable.btn_primary)
        btn.id = position

        btn.setOnClickListener {
            //Checking if user has already voted for specific category.
            val intent: Intent? = if (list[position].hasVoted == 0) {
                Intent(mContext, CandidatesActivity::class.java)

            } else Intent(mContext, ReviewsActivity::class.java)

            intent?.run {
                putExtra(ELECTION_ID, list[position].electionId)
                putExtra(CATEGORY_ID, list[position].categoryId)
                putExtra(CATEGORY, list[position].category)

                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            mContext.startActivity(intent)
        }
        return btn
    }
}