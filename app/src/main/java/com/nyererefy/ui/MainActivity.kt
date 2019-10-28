package com.nyererefy.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nyererefy.R
import com.nyererefy.databinding.ActivityMainBinding
import com.nyererefy.utilities.Pref
import com.nyererefy.utilities.common.Constants.IS_ACCOUNT_SET
import com.nyererefy.utilities.common.Constants.NAME
import com.nyererefy.utilities.common.Constants.USERNAME
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private lateinit var appBarConfig: AppBarConfiguration
    private lateinit var bind: ActivityMainBinding
    private lateinit var pref: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)

        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)

        appBarConfig = AppBarConfiguration(navController.graph, bind.drawerLayout)

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfig)
        bind.navView.setupWithNavController(navController)

        pref = Pref(this)
        val header = bind.navView.getHeaderView(0)

        if (pref.isLoggedIn) {
            header.name.text = pref.get(NAME)
            header.username.text = pref.get(USERNAME)

            val isAccountSet = pref.sharedPref.getBoolean(IS_ACCOUNT_SET, false)

            if (!isAccountSet) {
                startActivity<SetupActivity>()
            }
        } else {
            header.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        when {
            !pref.isLoggedIn -> {
                bind.navView.menu.removeItem(R.id.settings)
                bind.navView.menu.removeItem(R.id.profile)
                bind.navView.menu.removeItem(R.id.logout)
            }
            else -> bind.navView.menu.removeItem(R.id.login)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
