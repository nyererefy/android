package com.nyererefy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nyererefy.R
import com.nyererefy.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) //Splash finished.
        super.onCreate(savedInstanceState)

        val bind: ActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main)

        navController = findNavController(R.id.nav_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Set up ActionBar
        setSupportActionBar(bind.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Set up Bottom Navigation menu
        bind.bottomNavigation.setupWithNavController(navController)

        checkLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun checkLogin() {
        /*if (!getLoginPreference(this)) {
            clearPreferences(this)
            startActivity<LoginActivity>()
        }*/
    }

}
