package com.konektedi.vs.utilities.common

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.konektedi.vs.R
import com.konektedi.vs.utilities.common.Constants.ID
import java.util.*

fun getRandomColor(context: Context): String? {
    val colors: Array<out String> = context.resources.getStringArray(R.array.articleColors)
    return colors.random()
}

/*Returns random color from colorList*/
fun <E> Array<E>.random(): E? = if (size > 0) get(Random().nextInt(size)) else null

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


