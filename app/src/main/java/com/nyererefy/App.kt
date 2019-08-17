package com.nyererefy

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.rohitss.uceh.UCEHandler


class App : MultiDexApplication() {
    companion object {
        lateinit var appContext: Context

//        /**
//         * For solving drawableStart in TextViews problems. and ahbottomnavigation
//         */
//        init {
//            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
//        }
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this

        UCEHandler.Builder(this).build()
    }

}