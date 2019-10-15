package com.nyererefy.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nyererefy.utilities.common.NetworkState
import org.jetbrains.anko.design.indefiniteSnackbar

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

/**
 * For showing refreshing on SwipeRefreshLayout.
 */
@BindingAdapter("showRefreshing", "retry")
fun bindProgress(layout: SwipeRefreshLayout, networkState: NetworkState, retry: () -> Unit) {
    when (networkState) {
        NetworkState.LOADING -> layout.isRefreshing = true
        else -> {
            layout.isRefreshing = false

            networkState.msg?.run {
                layout.indefiniteSnackbar(this, "Retry") { retry() }
            }
        }
    }
}

@BindingAdapter("userId", "studentId")
fun hideIfNotOwner(view: View, userId: String?, studentId: String?) {
    view.visibility = if (userId != studentId) View.GONE else View.VISIBLE
}
