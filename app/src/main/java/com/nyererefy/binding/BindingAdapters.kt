package com.nyererefy.binding

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nyererefy.App.Companion.appContext
import com.nyererefy.R
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.common.Status
import org.jetbrains.anko.design.longSnackbar

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("showProgress")
fun bindProgress(layout: SwipeRefreshLayout, status: Status?) {
    when (status) {
        Status.LOADING -> layout.isRefreshing = true
        else -> layout.isRefreshing = false
    }
}

/**
 * For showing progress bar when network state is loading.
 */
@BindingAdapter("app:showLoading")
fun showProgressBar(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADING -> View.VISIBLE
        else -> View.GONE
    }
}

/**
 * For hiding any layout when network state is loading.
 */
@BindingAdapter("app:hideWhenLoading")
fun hideWhenLoading(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADING -> View.GONE
        else -> View.VISIBLE
    }
}

@BindingAdapter("app:showAfterSuccess")
fun showAfterSuccess(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADED -> View.VISIBLE
        else -> View.GONE
    }
}

@BindingAdapter("app:hideAfterSuccess")
fun hideAfterSuccess(view: View, networkState: NetworkState?) {
    view.visibility = when (networkState) {
        NetworkState.LOADED -> View.GONE
        else -> View.VISIBLE
    }
}

/**
 * For showing long snackBar with retry when error occurred.
 */
@BindingAdapter("app:handleError", "app:handleRetry")
fun handleErrorAndRetry(view: View, networkState: NetworkState?, retry: () -> Unit) {
    networkState?.msg?.run {
        view.longSnackbar(this, appContext.getString(R.string.retry)) { retry() }
    }
}

/**
 * For showing error in EditText.
 */
@BindingAdapter("app:showError")
fun showError(editText: EditText, error: Int?) {
    error?.run {
        editText.error = appContext.getString(error)
    }
}
