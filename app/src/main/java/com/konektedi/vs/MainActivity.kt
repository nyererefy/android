package com.konektedi.vs

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.PopupMenu
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.konektedi.vs.elections.ElectionsFragment
import com.konektedi.vs.motions.MotionsFragment
import com.konektedi.vs.news.NewsFragment
import com.konektedi.vs.other.SupportActivityMain
import com.konektedi.vs.student.*
import com.rohitss.uceh.UCEHandler
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    companion object {
        //TO UNDERSTAND WHAT IS DONE HERE
        //https://stackoverflow.com/questions/7491287/android-how-to-use-sharedpreferences-in-non-activity-class
        lateinit var contextOfApplication: Context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkLogin()
        initBottomNav()
        centerTitle()

        contextOfApplication = applicationContext

        //Get clear errors
        UCEHandler.Builder(applicationContext).build()

        setSupportActionBar(toolbar)

    }

    private fun checkLogin() {
        if (!getLoginPreference(this)) {
            clearPreferences(this)
            startActivity<LoginActivity>()
        }
    }

    fun showPopupMenu(view: View) {
        PopupMenu(this, view, Gravity.CENTER_HORIZONTAL).run {
            menuInflater.inflate(R.menu.top_menu, menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.profile -> {
                        startActivity<StudentProfile>()
                    }
                    R.id.logout -> {
                        clearPreferences(this@MainActivity)
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
                true
            }
            show()
        }
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

    private fun initBottomNav() {
        openFragment(ElectionsFragment())

        bottomNavigationBar
                .addItem(BottomNavigationItem(R.drawable.ic_email, getString(R.string.title_elections)))
                .addItem(BottomNavigationItem(R.drawable.ic_menu_camera, getString(R.string.title_motions)))
                .addItem(BottomNavigationItem(R.drawable.ic_dashboard_black_24dp, getString(R.string.title_news)))
                .addItem(BottomNavigationItem(R.drawable.icon_facebook, getString(R.string.title_more)))
                .setFirstSelectedPosition(0)
                .initialise()

        bottomNavigationBar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabReselected(position: Int) {
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> openFragment(ElectionsFragment())
                    1 -> openFragment(MotionsFragment())
                    2 -> openFragment(NewsFragment())
                    3 -> showPopupMenu(coordinatorLayout)
                }
            }

        })

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commitNowAllowingStateLoss()
    }

    private fun centerTitle() {
        val textViews = ArrayList<View>()
        window.decorView.findViewsWithText(textViews, title, View.FIND_VIEWS_WITH_TEXT)
        if (textViews.size > 0) {
            var appCompatTextView: AppCompatTextView? = null
            if (textViews.size == 1)
                appCompatTextView = textViews[0] as AppCompatTextView
            else {
                for (v in textViews) {
                    if (v.parent is Toolbar) {
                        appCompatTextView = v as AppCompatTextView
                        break
                    }
                }
            }
            if (appCompatTextView != null) {
                val params = appCompatTextView.layoutParams
                params.width = ViewGroup.LayoutParams.MATCH_PARENT
                appCompatTextView.layoutParams = params
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    appCompatTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
                }
            }
        }
    }

    override fun onBackPressed() {
        finishAffinity()
        System.exit(0)
    }
}
