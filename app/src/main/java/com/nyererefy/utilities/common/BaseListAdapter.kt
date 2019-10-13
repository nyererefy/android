package com.nyererefy.utilities.common

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nyererefy.R

abstract class BaseListAdapter<T, V : ViewDataBinding>(
        diffCallback: DiffUtil.ItemCallback<T>,
        private val retryCallback: () -> Unit
) : ListAdapter<T, RecyclerView.ViewHolder>(diffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = createBinding(parent)

        return when (viewType) {
            R.layout.network_state_item -> NetworkStateItemViewHolder.create(parent, retryCallback)
            else -> BaseViewHolder(binding)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.network_state_item -> (holder as NetworkStateItemViewHolder).bindTo(networkState)
            else -> {
                val vh = holder as BaseViewHolder<V>

                bind(vh.binding, getItem(position))
                vh.binding.executePendingBindings()
            }
        }
    }

    protected abstract fun createBinding(parent: ViewGroup): V

    protected abstract fun bind(binding: V, item: T)

    protected abstract val layout: Int

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.network_state_item
        } else layout
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
}