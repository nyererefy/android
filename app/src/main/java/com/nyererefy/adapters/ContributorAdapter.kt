package com.nyererefy.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.nyererefy.R
import com.nyererefy.databinding.ListGithubProfileBinding
import com.nyererefy.github.graphql.CollaboratorsQuery
import com.nyererefy.utilities.common.BaseListAdapter


class ContributorAdapter(retryCallback: () -> Unit)
    : BaseListAdapter<CollaboratorsQuery.Node, ListGithubProfileBinding>(COMPARATOR, retryCallback) {

    override val layout = R.layout.list_github_profile

    override fun createBinding(parent: ViewGroup): ListGithubProfileBinding {
        return ListGithubProfileBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
        )
    }

    override fun bind(binding: ListGithubProfileBinding, item: CollaboratorsQuery.Node, position: Int) {
        binding.apply {
            node = item
            this.onEmailClicked = View.OnClickListener {
            }
            this.onWebClicked = View.OnClickListener {
            }
        } //todo
    }

    companion object {
        val COMPARATOR = object : DiffUtil.ItemCallback<CollaboratorsQuery.Node>() {
            override fun areContentsTheSame(old: CollaboratorsQuery.Node, new: CollaboratorsQuery.Node) = old == new

            override fun areItemsTheSame(old: CollaboratorsQuery.Node, new: CollaboratorsQuery.Node) = old.id() == new.id()
        }
    }
}