package com.nyererefy.binding

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nyererefy.App.Companion.appContext
import com.nyererefy.R
import com.nyererefy.utilities.common.NetworkState
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar

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

/**
 * For executing refresh on SwipeRefreshLayout.
 */
@BindingAdapter("onRefresh")
fun handleRefreshing(swipeRefreshLayout: SwipeRefreshLayout, onRefresh: () -> Unit) {
    swipeRefreshLayout.setOnRefreshListener {
        onRefresh()
    }
}

@BindingAdapter("userId", "studentId")
fun hideIfNotOwner(view: View, userId: String?, studentId: String?) {
    view.visibility = if (userId != studentId) View.GONE else View.VISIBLE
}

/**
 * For showing progress bar when network state is loading.
 */
@BindingAdapter("showLoading")
fun showProgressBar(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADING -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * For hiding any layout when network state is loading.
 */
@BindingAdapter("hideWhenLoading")
fun hideWhenLoading(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADING -> View.GONE
        else -> View.VISIBLE
    }
}

@BindingAdapter("showAfterSuccess")
fun showAfterSuccess(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADED -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("hideAfterSuccess")
fun hideAfterSuccess(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADED -> View.GONE
        else -> View.VISIBLE
    }
}

/**
 * For showing long snackBar with retry when error occurred.
 */
@BindingAdapter("handleError", "handleRetry")
fun handleErrorAndRetry(view: View, networkState: NetworkState?, retry: () -> Unit) {
    networkState?.msg?.run {
        view.longSnackbar(this, appContext.getString(R.string.retry)) { retry() }
    }
}

/**
 * For showing error in EditText.
 */
@BindingAdapter("showError")
fun showError(editText: EditText, error: Int?) {
    error?.run {
        editText.error = appContext.getString(error)
    }
}

@BindingAdapter("setTextFromInt")
fun setTextFromInt(textView: TextView, int: Int) {
    textView.text = "$int"
}