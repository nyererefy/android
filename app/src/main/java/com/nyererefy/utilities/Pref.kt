package com.nyererefy.utilities

import android.content.Context
import android.content.SharedPreferences
import com.nyererefy.utilities.common.Constants.ID
import com.nyererefy.utilities.common.Constants.NYEREREFY_PREFERENCES

class Pref(context: Context) {
    val sharedPref: SharedPreferences = context.getSharedPreferences(NYEREREFY_PREFERENCES, Context.MODE_PRIVATE)

    val isLoggedIn: Boolean = sharedPref.getString(ID, null) != null

    val studentId: String? = sharedPref.getString(ID, "")

    fun get(key: String): String? {
        return sharedPref.getString(key, "")
    }

    fun save(key: String, v: String) {
        val editor = sharedPref.edit()
        editor.putString(key, v)
        editor.apply()
    }

    fun clear() {
        val editor = sharedPref.edit()
        editor.clear().apply()
    }
}

