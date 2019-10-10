package com.nyererefy.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nyererefy.R
import com.nyererefy.databinding.ActivityMainBinding
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) //Splash finished.
        super.onCreate(savedInstanceState)

        val bind: ActivityMainBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_main)

        navController = findNavController(R.id.nav_fragment)

        // Set up Bottom app bar
        bind.bottomAppBar.setupWithNavController(navController)

        checkLogin()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    private fun checkLogin() {
        /*if (!getLoginPreference(this)) {
            clearPreferences(this)
            startActivity<LoginActivity>()
        }*/
    }

}
