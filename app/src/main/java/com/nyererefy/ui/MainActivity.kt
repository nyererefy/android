package com.nyererefy.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.aurelhubert.ahbottomnavigation.notification.AHNotification
import com.nyererefy.BuildConfig
import com.nyererefy.R
import com.nyererefy.ui.fragments.ElectionsFragment
import com.nyererefy.ui.fragments.MotionsFragment
import com.nyererefy.ui.fragments.UserFragment
import com.nyererefy.data.Api
import com.nyererefy.utilities.common.Constants.BASE_URL_WITH_NO_VERSION
import com.nyererefy.utilities.models.AppUpdate
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme) //Splash finished.
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        checkLogin()
        centerTitle()
        initBottomNav()
        setupViewPager(viewPager)
        checkUpdates()
    }

    private fun checkLogin() {
//        if (!getLoginPreference(this)) {
//            clearPreferences(this)
//            startActivity<LoginActivity>()
//        }
    }

    private fun initBottomNav() {
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_elections), R.drawable.ic_elections))
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_motions), R.drawable.ic_motions))
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.title_more), R.drawable.ic_user))

        bottomNavigation.setOnTabSelectedListener { position, _ ->
            when (position) {
                0 -> viewPager.currentItem = 0
                1 -> viewPager.currentItem = 1
                2 -> viewPager.currentItem = 2
            }
            true
        }

        bottomNavigation.isBehaviorTranslationEnabled = false
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

        bottomNavigation.setNotification(notification, 0)
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
        exitProcess(0)
    }

    private fun checkUpdates() {
        val apiClient = Api.create(false, BASE_URL_WITH_NO_VERSION)

        apiClient.checkUpdate().enqueue(
                object : Callback<AppUpdate> {
                    override fun onFailure(call: Call<AppUpdate>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<AppUpdate>, response: Response<AppUpdate>) {
                        if (response.isSuccessful) {
                            val result = response.body()
                            if (BuildConfig.VERSION_CODE < result!!.versionCode) {
                                //Convert this strings to new lines.
                                alert(result.newFeatures.joinToString(
                                        "\r\n",
                                        "What\'s new! \r\n \r\n",
                                        "\n ${result.warning}"),
                                        "New update available")
                                {
                                    positiveButton("Update Now") {

                                        val uri = Uri.parse("market://details?id=$packageName")
                                        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY or
                                                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                                        }
                                        try {
                                            startActivity(goToMarket)
                                        } catch (e: ActivityNotFoundException) {
                                            startActivity(Intent(Intent.ACTION_VIEW,
                                                    Uri.parse("http://play.google.com/store/apps/details?id=$packageName")))
                                        }
                                    }
                                    negativeButton("Later") { it.dismiss() }
                                    isCancelable = false
                                }.show()
                            }
                        }
                    }
                })
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = SectionsPagerAdapter(supportFragmentManager)

        adapter.addFragment(ElectionsFragment())
        adapter.addFragment(MotionsFragment())
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
            return fragmentList.size
        }

        internal fun addFragment(fragment: Fragment) {
            fragmentList.add(fragment)
        }
    }
}
