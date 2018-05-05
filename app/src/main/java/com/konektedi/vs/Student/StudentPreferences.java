package com.konektedi.vs.Student;

import android.content.Context;
import android.content.SharedPreferences;

import static com.konektedi.vs.Utilities.Constants.VSPreferences;
import static com.konektedi.vs.Utilities.Constants.IS_LOGGED_IN;

public class StudentPreferences {
    public static void savePreference(Context context, String key, String value) {
        SharedPreferences sharedPref = context.getSharedPreferences(VSPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(Context context, String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(VSPreferences, Context.MODE_PRIVATE);
        return sharedPref.getString(key, "");
    }

    public static Boolean getLoginPreference(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(VSPreferences, Context.MODE_PRIVATE);
        return sharedPref.getBoolean(IS_LOGGED_IN, false);
    }

    public static void clearPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(VSPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear().apply();
    }
}
