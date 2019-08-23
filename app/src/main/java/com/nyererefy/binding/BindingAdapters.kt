package com.nyererefy.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nyererefy.utilities.Status

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
        Status.SUCCESS -> layout.isRefreshing = false
        Status.ERROR -> layout.isRefreshing = false
    }
}
