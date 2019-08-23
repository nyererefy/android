package com.nyererefy.adapters.viewholders

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nyererefy.R
import com.nyererefy.databinding.ListItemElectionBinding
import com.nyererefy.graphql.ElectionsQuery
import com.nyererefy.ui.fragments.ElectionsFragmentDirections

class ElectionViewHolder(private val binding: ListItemElectionBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ElectionsQuery.Election) {
        val listener = createOnClickListener(item.id())

        binding.apply {
            clickListener = listener
            election = item
            executePendingBindings()
        }
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val nav = ElectionsFragmentDirections.actionElectionsToCategoriesFragment(id)
            it.findNavController().navigate(nav)
        }
    }
}