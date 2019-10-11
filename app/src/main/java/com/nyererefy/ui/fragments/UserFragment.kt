package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nyererefy.R
import com.nyererefy.di.Injectable
import com.nyererefy.ui.fragments.base.BaseFragment


class UserFragment : BaseFragment(), Injectable {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

//    private fun showDetails(rootView: View) {
//        val usernameView = rootView.findViewById(R.id.usernameView) as TextView
//        val nameView = rootView.findViewById(R.id.nameView) as TextView
//        val dpView = rootView.findViewById(R.id.dpView) as ImageView
//
//        usernameView.text = name
//        nameView.text = username
//
//        //TODO LoadDp
//        Glide.with(activity!!)
//                .load("")
//                .apply(RequestOptions()
//                        .placeholder(R.drawable.holder)
//                        .error(R.drawable.holder)
//                )
//                .into(dpView)
//    }
//
//    private fun initMenu(rootView: View) {
////        val logout = rootView.findViewById(R.id.logout) as TextView
//        val about = rootView.findViewById(R.id.about) as TextView
//        val support = rootView.findViewById(R.id.support) as TextView
//
//        about.setOnClickListener { showAbout() }
////        logout.setOnClickListener {
////            clearPreferences(activity!!)
////            startActivity<LoginActivity>()
////        }
//        support.setOnClickListener {
//            clearPreferences(activity!!)
//            startActivity<SupportActivityMain>()
//        }
//
//    }
//
//    private fun showAbout() {
//        var version = ""
//
//        try {
//            val pInfo = activity!!.packageManager.getPackageInfo(activity!!.packageName, 0)
//            version = pInfo.versionName
//
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//
//        val builder = AlertDialog.Builder(activity!!)
//
//        val inflater = layoutInflater
//        val dialogView = inflater.inflate(R.layout.v_about, null)
//        val versionView = dialogView.findViewById<TextView>(R.id.versionView)
//
//        val versionTitle = "Version: $version"
//
//        versionView.text = versionTitle
//
//        builder.setCancelable(true)
//        builder.setView(dialogView)
//
//        val dialog = builder.create()
//        dialog.show()
//    }


}