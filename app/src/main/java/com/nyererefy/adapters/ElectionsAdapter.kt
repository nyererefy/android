package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nyererefy.R
import com.nyererefy.adapters.viewholders.ElectionViewHolder
import com.nyererefy.databinding.ListItemElectionBinding
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.common.NetworkStateItemViewHolder


class ElectionsAdapter(
        private val retryCallback: () -> Unit
) : PagedListAdapter<ElectionsQuery.Election, RecyclerView.ViewHolder>(POST_COMPARATOR) {
    private var networkState: NetworkState? = null

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_item_election -> (holder as ElectionViewHolder).bind(getItem(position)!!)
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
        }
    }

    override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int,
            payloads: MutableList<Any>) {
        if (payloads.isNotEmpty()) {
            val item = getItem(position)
            (holder as ElectionViewHolder).bind(item!!)
        } else {
            onBindViewHolder(holder, position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_election -> ElectionViewHolder(ListItemElectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
            ))
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else {
            R.layout.list_item_election
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
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<ElectionsQuery.Election>() {
            override fun areContentsTheSame(old: ElectionsQuery.Election, new: ElectionsQuery.Election) =
                    old == new

            override fun areItemsTheSame(old: ElectionsQuery.Election, new: ElectionsQuery.Election) =
                    old.id() == new.id()
        }

    }
}
