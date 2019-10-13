package com.nyererefy.adapters.viewholders

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nyererefy.databinding.ListItemSubcategoryBinding
import com.nyererefy.graphql.SubcategoriesQuery
import com.nyererefy.ui.fragments.SubcategoriesFragmentDirections

class SubcategoryViewHolder(private val binding: ListItemSubcategoryBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: SubcategoriesQuery.Subcategory) {
        val listener = createOnClickListener(item.id())

        binding.apply {
            clickListener = listener
            subcategory = item
            executePendingBindings()
        }
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            val direction = SubcategoriesFragmentDirections.actionCategoriesToCandidatesFragment(id)
            it.findNavController().navigate(direction)
        }
    }
}