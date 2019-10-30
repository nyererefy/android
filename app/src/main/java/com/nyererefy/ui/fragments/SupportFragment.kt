package com.nyererefy.ui.fragments

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.nyererefy.databinding.FragmentSupportBinding
import org.jetbrains.anko.support.v4.browse
import org.jetbrains.anko.support.v4.email

class SupportFragment : Fragment() {
    private lateinit var packageManager: PackageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        packageManager = this.requireActivity().packageManager
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSupportBinding.inflate(inflater, container, false)
        binding.fragment = this
        return binding.root
    }

    fun openBlog() {
        browse("https://nyererefy.blogspot.com")
    }

    fun openMail() {
        email("nyererefy@gmail.com", "Android feedback")
    }

    fun openFacebookPage() {
        try {
            packageManager.getPackageInfo("com.facebook.katana", 0)

            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("fb://page/1967247010153586"))
            )
        } catch (e: Exception) {
            browse("https://www.facebook.com/nyererefy")
        }
    }

    fun openInstagram() {
        val intent = Intent(Intent.ACTION_VIEW)

        if (packageManager.getPackageInfo("com.instagram.android", 0) != null) {
            // http://stackoverflow.com/questions/21505941/intent-to-open-instagram-user-profile-on-android
            intent.data = Uri.parse("http://instagram.com/_u/nyererefy")
            intent.setPackage("com.instagram.android")
        }

        try {
            startActivity(intent)
        } catch (ignored: PackageManager.NameNotFoundException) {
            browse("https://www.instagram.com/nyererefy")
        }
    }

    fun openTwitter() {
        try {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?screen_name=nyererefy"))
            )
        } catch (e: Exception) {
            startActivity(Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/#!/nyererefy"))
            )
        }
    }

    fun rateThisApp() {
        val packageName = this.requireActivity().packageName
        val uri = Uri.parse("market://details?id=$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)

        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
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
}
