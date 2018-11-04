package com.konektedi.vs.utilities.common

import android.content.Context
import android.support.v4.content.ContextCompat.startActivity
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants.ID


fun loadDpThumbnails(view: ImageView, dpUrl: String?, userId: Int, mContext: Context) {

    Glide.with(mContext)
            .load(dpUrl)
            .apply(RequestOptions()
                    .placeholder(R.drawable.ic_placeholder_user)
                    .error(R.drawable.ic_placeholder_user)
            )
            .into(view)

    view.setOnClickListener { openKonektediProfile(id = userId, mContext = mContext) }
}

//TODO not done here.
fun openKonektediProfile(id: Int, mContext: Context) {
    val intent = mContext
            .packageManager
            .getLaunchIntentForPackage("com.konektedi.konektedi.user.profile.UserProfile")

    if (intent != null) {
        intent.putExtra(ID, id)
//        startActivity(intent)
    } else {
        Toast.makeText(mContext, "You need to Install konektedi App", Toast.LENGTH_SHORT).show()
    }
}


