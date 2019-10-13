package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListItemSubcategoryBinding
import com.nyererefy.graphql.SubcategoriesQuery.Subcategory
import com.nyererefy.ui.fragments.SubcategoriesFragmentDirections
import com.nyererefy.utilities.common.BaseListAdapter


class SubcategoriesAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<Subcategory, ListItemSubcategoryBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_item_subcategory

    override fun createBinding(parent: ViewGroup): ListItemSubcategoryBinding {
        return ListItemSubcategoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListItemSubcategoryBinding, item: Subcategory) {
        binding.apply {
            subcategory = item
            executePendingBindings()
            clickListener = View.OnClickListener {
                val direction = SubcategoriesFragmentDirections.actionCategoriesToCandidatesFragment(item.id())
                it.findNavController().navigate(direction)
            }
        }
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<Subcategory>() {
            override fun areContentsTheSame(old: Subcategory, new: Subcategory) = old == new

            override fun areItemsTheSame(old: Subcategory, new: Subcategory) = old.id() == new.id()
        }
    }
}