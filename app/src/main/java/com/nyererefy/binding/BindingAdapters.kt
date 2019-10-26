package com.nyererefy.binding

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nyererefy.App.Companion.appContext
import com.nyererefy.R
import com.nyererefy.utilities.common.NetworkState
import com.nyererefy.utilities.common.SubscriptionState
import com.nyererefy.utilities.setViewColor
import org.jetbrains.anko.design.indefiniteSnackbar
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.design.snackbar

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
@BindingAdapter("hideWhenLoading", "isGoneBefore", requireAll = false)
fun hideWhenLoading(view: View, networkState: NetworkState?, isGoneBefore: Boolean?) {
    // Control view behavior before network state comes into play.
    if (isGoneBefore != null && networkState == null ||
            isGoneBefore != null && networkState == NetworkState.LOADED) {
        view.visibility = if (isGoneBefore) {
            View.GONE
        } else {
            View.VISIBLE
        }
    } else {
        view.visibility = when (networkState) {
            NetworkState.LOADING -> View.GONE
            else -> View.VISIBLE
        }
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

@BindingAdapter("clearAfterSuccess")
fun clearAfterSuccess(editText: EditText, networkState: NetworkState?) {
    when (networkState) {
        NetworkState.LOADED -> editText.setText("")
    }
}


@BindingAdapter("handleSubscription", "retry")
fun handleSubscription(view: View, subState: SubscriptionState?, retry: () -> Unit) {
    when (subState) {
        SubscriptionState.CONNECTING -> {
            view.snackbar(appContext.getString(R.string.connecting))
        }
        SubscriptionState.CONNECTED -> {
            view.snackbar(appContext.getString(R.string.connected))
        }
        SubscriptionState.FAILED -> {
            view.indefiniteSnackbar(
                    appContext.getString(R.string.failed_to_subscribe),
                    appContext.getString(R.string.retry))
            { retry() }
        }
        else -> {
        }
    }
}

@BindingAdapter("showSubscriptionStateSign")
fun showSubscriptionStateSign(view: View, subState: SubscriptionState?) {
    when (subState) {
        SubscriptionState.CONNECTING -> {
            view.setViewColor(R.color.grey_20)
        }
        SubscriptionState.CONNECTED -> {
            view.setViewColor(R.color.blue)
        }
        SubscriptionState.RESPONDED -> {
            view.setViewColor(R.color.green)
        }
        SubscriptionState.TERMINATED -> {
            view.setViewColor(R.color.brown)
        }
        SubscriptionState.COMPLETED -> {
            view.setViewColor(R.color.lime)
        }
        SubscriptionState.FAILED -> {
            view.setViewColor(R.color.red)
        }
    }
}