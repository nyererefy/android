package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.nyererefy.adapters.viewholders.SubcategoryViewHolder
import com.nyererefy.databinding.ListItemSubcategoryBinding
import com.nyererefy.graphql.SubcategoriesQuery.Subcategory

/**
 * Created by Sy on b/14/2018.
 */

class SubcategoriesAdapter : ListAdapter<Subcategory, SubcategoryViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubcategoryViewHolder {
        return SubcategoryViewHolder(ListItemSubcategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: SubcategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

private class DiffCallback : DiffUtil.ItemCallback<Subcategory>() {
    override fun areItemsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
        return oldItem.id() == newItem.id()
    }

    override fun areContentsTheSame(oldItem: Subcategory, newItem: Subcategory): Boolean {
        return oldItem == newItem
    }
}