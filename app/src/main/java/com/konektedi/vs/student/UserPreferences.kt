package com.konektedi.vs.student

import android.content.Context
import com.konektedi.vs.utilities.common.Constants.IS_LOGGED_IN
import com.konektedi.vs.utilities.common.Constants.NYEREREFY_PREFERENCES

fun savePreference(context: Context, key: String, value: String) {
    val sharedPref = context.getSharedPreferences(NYEREREFY_PREFERENCES, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(key, value)
    editor.apply()
}

fun grabPreference(context: Context, key: String): String {
    val sharedPref = context.getSharedPreferences(NYEREREFY_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPref.getString(key, "")
}

fun getLoginPreference(context: Context): Boolean {
    val sharedPref = context.getSharedPreferences(NYEREREFY_PREFERENCES, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(IS_LOGGED_IN, false)
}

fun clearPreferences(context: Context) {
    val sharedPreferences = context.getSharedPreferences(NYEREREFY_PREFERENCES, Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.clear().apply()
}
