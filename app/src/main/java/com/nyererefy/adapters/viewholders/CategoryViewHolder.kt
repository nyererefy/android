package com.nyererefy.adapters.viewholders

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nyererefy.R
import com.nyererefy.databinding.ListItemCategoryBinding
import com.nyererefy.graphql.CategoriesQuery

class CategoryViewHolder(private val binding: ListItemCategoryBinding)
    : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CategoriesQuery.Category) {
        val listener = createOnClickListener(item.id())

        binding.apply {
            clickListener = listener
            category = item
            executePendingBindings()
        }
    }

    private fun createOnClickListener(id: String): View.OnClickListener {
        return View.OnClickListener {
            it.findNavController().navigate(R.id.surveys)
        }
    }
}