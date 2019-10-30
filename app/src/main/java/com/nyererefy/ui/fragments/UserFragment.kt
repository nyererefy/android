package com.nyererefy.ui.fragments

import android.os.Bundle
import android.view.*
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.nyererefy.R
import com.nyererefy.databinding.FragmentProfileBinding
import com.nyererefy.ui.LoginActivity
import com.nyererefy.utilities.common.BaseFragment
import com.nyererefy.utilities.common.Constants.NAME
import com.nyererefy.utilities.common.Constants.USERNAME
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.support.v4.intentFor
import javax.inject.Inject


class UserFragment : BaseFragment() {
    @Inject
    lateinit var cookieJar: PersistentCookieJar

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val bind = FragmentProfileBinding.inflate(inflater, container, false)

        bind.nameView.text = pref.get(NAME)
        bind.usernameView.text = pref.get(USERNAME)

        setHasOptionsMenu(true)

        return bind.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.user_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> logout()
        }
        return super.onOptionsItemSelected(item)
    }


//    private fun showDetails(rootView: View) {
//        val usernameView = rootView.findViewById(R.id.usernameView) as TextView
//        val nameView = rootView.findViewById(R.id.nameView) as TextView
//        val dpView = rootView.findViewById(R.id.dpView) as ImageView
//
//        usernameView.text = name
//        nameView.text = username
//
//
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
//            startActivity<SupportFragment>()
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

    private fun logout() {
        //todo call server too.
        pref.clear()
        cookieJar.clear()

        //clear google login
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
        GoogleSignIn.getClient(requireActivity(), gso).signOut()

        startActivity(intentFor<LoginActivity>().clearTop())
    }

}