package com.konektedi.vs.home.reviews

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.konektedi.vs.R
import com.konektedi.vs.utilities.models.Result

class ResultsAdapter(private val mContext: Context, private val candidatesList: List<Result>?) :
        RecyclerView.Adapter<ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.z_result, parent, false)

        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
        return candidatesList?.size!!
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(candidate = candidatesList?.get(position), mContext = mContext)
    }

}