package com.nyererefy.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.iid.FirebaseInstanceId
import com.nyererefy.R
import com.nyererefy.databinding.ActivityMainBinding
import com.nyererefy.graphql.type.FirebaseTokenInput
import com.nyererefy.utilities.Pref
import com.nyererefy.utilities.common.Constants.NAME
import com.nyererefy.utilities.common.Constants.USERNAME
import com.nyererefy.viewmodels.MainActivityViewModel
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nav_header.view.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.intentFor
import timber.log.Timber
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var cookieJar: PersistentCookieJar
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MainActivityViewModel by viewModels { viewModelFactory }
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

    }

    override fun onResume() {
        super.onResume()

        pref = Pref(this)

        val header = bind.navView.getHeaderView(0)

        if (pref.isLoggedIn) {
            //Showing details
            header.name.text = pref.get(NAME)
            header.username.text = pref.get(USERNAME)
            header.logout.setOnClickListener {
                logout()
            }
            //Hiding login menu item.
            bind.navView.menu.removeItem(R.id.login)
        } else {
            //Hiding header.
            header.visibility = View.GONE

            //Removing menu items.
            bind.navView.menu.removeItem(R.id.settings)
            bind.navView.menu.removeItem(R.id.profile)
            bind.navView.menu.removeItem(R.id.logout)

            //Todo login btn disappears when log out
        }

        sendNotification()
    }

    private fun sendNotification() {
        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener(this) {
            val token = it.token
            Timber.d("token $token")
            val deviceId = FirebaseInstanceId.getInstance().id
            val input = FirebaseTokenInput.builder().deviceId(deviceId).token(token).build()
            viewModel.setInput(input)
            viewModel.data.observe(this, Observer { data ->
                if (data.createFirebaseToken()) {
                    Timber.d("Token received by the server.")
                }
            })
        }
    }

    private fun logout() {
        //todo call server too.
        pref.clear()
        cookieJar.clear()

        //clear google login
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        GoogleSignIn.getClient(this, gso).signOut()

        startActivity(intentFor<LoginActivity>().clearTop())
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
