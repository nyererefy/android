package com.nyererefy.utilities

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import java.util.*

@Suppress("DEPRECATION")
fun TextView.htmlText(text: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        setText(Html.fromHtml(text, Html.FROM_HTML_MODE_LEGACY))
    } else {
        setText(Html.fromHtml(text))
    }
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

fun Uri.getMimeType(context: Context): String? {
    return if (this.scheme == ContentResolver.SCHEME_CONTENT) {
        val cr = context.contentResolver
        cr.getType(this)
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(this.toString())
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                fileExtension.toLowerCase(Locale.ENGLISH))
    }
}