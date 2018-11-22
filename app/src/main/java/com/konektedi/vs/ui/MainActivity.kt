package com.konektedi.vs.ui

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.konektedi.vs.R
import com.konektedi.vs.elections.ElectionsFragment
import com.konektedi.vs.motions.MotionsFragment
import com.konektedi.vs.news.NewsFragment
import com.konektedi.vs.other.SupportActivityMain
import com.konektedi.vs.student.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        checkLogin()
        initBottomNav()
        centerTitle()
        setupViewPager(viewPager)
    }

    private fun checkLogin() {
        if (!getLoginPreference(this)) {
            clearPreferences(this)
            startActivity<LoginActivity>()
        }
    }

    private fun showPopupMenu(view: View) {
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
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_elections), R.drawable.ic_elections))
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_motions), R.drawable.ic_motions))
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_news), R.drawable.ic_news))
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_more), R.drawable.ic_user))

        bottomNavigation.setOnTabSelectedListener { position, _ ->
            when (position) {
                0 -> viewPager.currentItem = 0
                1 -> viewPager.currentItem = 1
                2 -> viewPager.currentItem = 2
                3 -> viewPager.currentItem = 3
            }
            true
        }

        bottomNavigation.accentColor = ContextCompat.getColor(this, R.color.accent_color)
        bottomNavigation.inactiveColor = ContextCompat.getColor(this, R.color.inactive_color)

        bottomNavigation.defaultBackgroundColor = ContextCompat.getColor(this, R.color.bn_background)
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_HIDE
        bottomNavigation.currentItem = 0

        val notification = AHNotification.Builder()
                .setText("5")
                .setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                .setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
                .build()

        bottomNavigation.setNotification(notification, 2)
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

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)

        adapter.addFragment(ElectionsFragment())
        adapter.addFragment(MotionsFragment())
        adapter.addFragment(NewsFragment())
        adapter.addFragment(UserFragment())
        viewPager.adapter = adapter

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottomNavigation.currentItem = position
            }

        })
    }

    private inner class SectionsPagerAdapter internal constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        private val fragmentList = ArrayList<Fragment>()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return 4
        }

        internal fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}
