package com.konektedi.vs

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.CoordinatorLayout
import android.support.design.widget.Snackbar
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast

import com.konektedi.vs.home.elections.ElectionsFragment
import com.konektedi.vs.motions.MotionsFragment
import com.konektedi.vs.news.NewsFragment
import com.konektedi.vs.other.SupportActivityMain
import com.konektedi.vs.student.*
import com.rohitss.uceh.UCEHandler

import java.util.ArrayList

import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLogin()

        contextOfApplication = applicationContext

        //Get clear errors
        UCEHandler.Builder(applicationContext).build()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val coordinatorLayout = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager>(R.id.container)
        val adapter = SectionsPagerAdapter(supportFragmentManager)

        adapter.addFragment(ElectionsFragment(), "elections")
        adapter.addFragment(MotionsFragment(), "motions")
        adapter.addFragment(NewsFragment(), "News")


        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

        if (!isNetworkAvailable) {
            val snackbar = Snackbar
                    .make(coordinatorLayout, "Internet is not connected", Snackbar.LENGTH_LONG)
                    .setAction("Connect") { Toast.makeText(this, "Okey", Toast.LENGTH_SHORT).show() }
            snackbar.show()
        }
    }

    private fun checkLogin() {
        if (!getLoginPreference(this)) {
            clearPreferences(this)
            startActivity<LoginActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile -> {
                startActivity<StudentProfile>()
            }
            R.id.logout -> {
                clearPreferences(this)
                startActivity<LoginActivity>()
            }
            R.id.settings -> {
                startActivity<Settings>()
            }
            R.id.about -> showAbout()
            R.id.support -> {
                startActivity<SupportActivityMain>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAbout() {
        var version = ""
        var verCode = 0

        try {
            val pInfo = this.packageManager.getPackageInfo(packageName, 0)
            version = pInfo.versionName
            verCode = pInfo.versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }

        val builder = AlertDialog.Builder(this)

        val inflater = layoutInflater
        val dialogView = inflater.inflate(R.layout.v_about, null)
        val versionView = dialogView.findViewById<TextView>(R.id.versionView)
        val verCodeView = dialogView.findViewById<TextView>(R.id.verCodeView)

        val versionTitle = "Version: $version"
        val versionCodeTitle = "Build: $verCode"

        versionView.text = versionTitle
        verCodeView.text = versionCodeTitle

        builder.setCancelable(true)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.show()
    }

    private inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList = ArrayList<Fragment>()
        private val fragmentTitles = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentTitles.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles[position]
        }

        internal fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            fragmentTitles.add(title)
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }

    companion object {
        //TO UNDERSTAND WHAT IS DONE HERE
        //https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class
        lateinit var contextOfApplication: Context
    }
}
