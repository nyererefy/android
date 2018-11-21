package com.konektedi.vs

import android.content.Context
import androidx.multidex.MultiDexApplication
import com.rohitss.uceh.UCEHandler

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        appContext = this

        UCEHandler.Builder(this).build()
    }

    companion object {
        lateinit var appContext: Context
    }
}