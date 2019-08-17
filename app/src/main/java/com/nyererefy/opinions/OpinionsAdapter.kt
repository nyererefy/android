package com.nyererefy.opinions

import androidx.paging.PagedListAdapter
import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import android.view.ViewGroup
import com.nyererefy.R
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.common.NetworkStateItemViewHolder
import com.nyererefy.utilities.models.Opinion

class OpinionsAdapter(private val mContext: Context, private val retryCallback: () -> Unit)
    : PagedListAdapter<Opinion, androidx.recyclerview.widget.RecyclerView.ViewHolder>(POST_COMPARATOR) {
    
    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.z_opinion -> (holder as OpinionViewHolder).bind(getItem(position), mContext)
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(
                    networkState)
        }
    }

    override fun onBindViewHolder(
            holder: androidx.recyclerview.widget.RecyclerView.ViewHolder,
            position: Int,
            payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as OpinionViewHolder).bind(item, mContext)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.z_opinion -> OpinionViewHolder.create(parent)
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.z_opinion
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<Opinion>() {
            override fun areContentsTheSame(oldItem: Opinion, newItem: Opinion): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: Opinion, newItem: Opinion): Boolean =
                    oldItem.opinionId == newItem.opinionId
        }

    }
}
