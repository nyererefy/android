//package com.nyererefy.ui
//
//import android.Manifest.permission.READ_CONTACTS
//import android.animation.Animator
//import android.animation.AnimatorListenerAdapter
//import android.app.Activity
//import android.app.LoaderManager.LoaderCallbacks
//import android.content.CursorLoader
//import android.content.Loader
//import android.content.pm.PackageManager
//import android.database.Cursor
//import android.net.Uri
//import android.os.AsyncTask
//import android.os.Build
//import android.os.Bundle
//import android.provider.ContactsContract
//import android.text.TextUtils
//import android.view.View
//import android.view.inputmethod.EditorInfo
//import android.widget.ArrayAdapter
//import android.widget.TextView
//import com.nyererefy.R
//import com.nyererefy.elections.getError
//import com.nyererefy.utilities.common.Constants.EMAIL
//import com.nyererefy.utilities.common.Constants.ID
//import com.nyererefy.utilities.common.Constants.IS_LOGGED_IN
//import com.nyererefy.utilities.common.Constants.NAME
//import com.nyererefy.utilities.common.Constants.NYEREREFY_PREFERENCES
//import com.nyererefy.utilities.common.Constants.PASSWORD
//import com.nyererefy.utilities.common.Constants.TOKEN
//import com.nyererefy.utilities.common.Constants.UNIVERSITY
//import com.nyererefy.utilities.common.Constants.USERNAME
//import com.nyererefy.utilities.models.User
//import kotlinx.android.synthetic.main.activity_login.*
//import okhttp3.ResponseBody
//import org.jetbrains.anko.startActivity
//import retrofit2.Response
//import java.util.*
//
///**
// * A login screen that offers login via email/password.
// */
//class LoginActivity : Activity(), LoaderCallbacks<Cursor> {
//    /**
//     * Keep track of the login task to ensure we can cancel it if requested.
//     */
//    private var mAuthTask: UserLoginTask? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_login)
//        // Set up the login form.
//        populateAutoComplete()
//        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
//            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                attemptLogin()
//                return@OnEditorActionListener true
//            }
//            false
//        })
//
//        sign_in_button.setOnClickListener { attemptLogin() }
//    }
//
//    private fun populateAutoComplete() {
//        if (!mayRequestContacts()) {
//            return
//        }
//
//        loaderManager.initLoader(0, null, this)
//    }
//
//    private fun mayRequestContacts(): Boolean {
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            return true
//        }
//        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
//            return true
//        }
//        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
//            // TODO: alert the user with a Snackbar/AlertDialog giving them the permission rationale
//            // To use the Snackbar from the design support library, ensure that the activity extends
//            // AppCompatActivity and uses the Theme.AppCompat theme.
//        } else {
//            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
//        }
//        return false
//    }
//
//    /**
//     * Callback received when a permissions request has been completed.
//     */
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
//                                            grantResults: IntArray) {
//        if (requestCode == REQUEST_READ_CONTACTS) {
//            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                populateAutoComplete()
//            }
//        }
//    }
//
//
//    /**
//     * Attempts to sign in or register the account specified by the login form.
//     * If there are form errors (invalid email, missing fields, etc.), the
//     * errors are presented and no actual login attempt is made.
//     */
//    private fun attemptLogin() {
//        if (mAuthTask != null) {
//            return
//        }
//
//        // Reset errors.
//        email.error = null
//        password.error = null
//        error_msg.visibility = View.GONE
//
//        // Store values at the time of the login attempt.
//        val emailStr = email.text.toString()
//        val passwordStr = password.text.toString()
//
//        var cancel = false
//        var focusView: View? = null
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
//            password.error = getString(R.string.error_invalid_password)
//            focusView = password
//            cancel = true
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(emailStr)) {
//            email.error = getString(R.string.error_field_required)
//            focusView = email
//            cancel = true
//        } else if (!isEmailValid(emailStr)) {
//            email.error = getString(R.string.error_invalid_email)
//            focusView = email
//            cancel = true
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView?.requestFocus()
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true)
//            mAuthTask = UserLoginTask(emailStr, passwordStr)
//            mAuthTask!!.execute(null as Void?)
//        }
//    }
//
//    private fun isEmailValid(email: String): Boolean {
//        return email.length in 3..100
//    }
//
//    private fun isPasswordValid(password: String): Boolean {
//        return password.length in 6..30
//    }
//
//    /**
//     * Shows the progress UI and hides the login form.
//     */
//    private fun showProgress(show: Boolean) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
//
//        login_form.visibility = if (show) View.GONE else View.VISIBLE
//        login_form.animate()
//                .setDuration(shortAnimTime)
//                .alpha((if (show) 0 else 1).toFloat())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        login_form.visibility = if (show) View.GONE else View.VISIBLE
//                    }
//                })
//
//        login_progress.visibility = if (show) View.VISIBLE else View.GONE
//        login_progress.animate()
//                .setDuration(shortAnimTime)
//                .alpha((if (show) 1 else 0).toFloat())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
//                    }
//                })
//
//    }
//
//    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
//        return CursorLoader(this,
//                // Retrieve elections rows for the device user's 'profile' contact.
//                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
//                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,
//
//                // Select only email addresses.
//                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
//                .CONTENT_ITEM_TYPE),
//
//                // Show primary email addresses first. Note that there won't be
//                // a primary email address if the user hasn't specified one.
//                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
//    }
//
//    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
//        val emails = ArrayList<String>()
//        cursor.moveToFirst()
//        while (!cursor.isAfterLast) {
//            emails.add(cursor.getString(ProfileQuery.ADDRESS))
//            cursor.moveToNext()
//        }
//
//        addEmailsToAutoComplete(emails)
//    }
//
//    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {
//
//    }
//
//    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {
//        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
//        val adapter = ArrayAdapter(this@LoginActivity,
//                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)
//
//        email.setAdapter(adapter)
//    }
//
//    object ProfileQuery {
//        val PROJECTION = arrayOf(
//                ContactsContract.CommonDataKinds.Email.ADDRESS,
//                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
//        val ADDRESS = 0
//        val IS_PRIMARY = 1
//    }
//
//    /**
//     * Represents an asynchronous login/registration task used to authenticate
//     * the user.
//     */
//    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String)
//        : AsyncTask<Void, Void, Boolean>() {
//
//        private var user: User? = null
//        private var error = ""
//
//        override fun doInBackground(vararg params: Void): Boolean? {
//            val map = HashMap<String, String>()
//
//            map[EMAIL] = mEmail
//            map[PASSWORD] = mPassword
//
//            val request = Api.create(true).authenticate(map)
//            try {
//                val response = request.execute()
//
//                when {
//                    response.isSuccessful -> {
//                        user = response.body()
//                        return true
//                    }
//                    else -> {
//                        error = getError(response as Response<ResponseBody>)!!
//                    }
//                }
//            } catch (e: InterruptedException) {
//                error = getString(R.string.unknown_error)
//            }
//            return false
//        }
//
//        override fun onPostExecute(success: Boolean?) {
//            mAuthTask = null
//            showProgress(false)
//
//            if (success!!) {
//                val preferences = getSharedPreferences(NYEREREFY_PREFERENCES, MODE_PRIVATE)
//
//                val editor = preferences.edit()
//                editor.clear()
//
//                editor.putString(ID, user?.id.toString())
//                editor.putString(NAME, user?.name)
//                editor.putString(USERNAME, user?.username)
//                editor.putString(UNIVERSITY, user?.universityId.toString())
//                editor.putString(TOKEN, user?.token)
//                editor.putBoolean(IS_LOGGED_IN, true)
//
//                editor.apply()
//                startActivity<MainActivity>()
//                finish()
//            } else {
//                error_msg.visibility = View.VISIBLE
//                error_msg.setHtml(error)
//            }
//        }
//
//        override fun onCancelled() {
//            mAuthTask = null
//            showProgress(false)
//        }
//    }
//
//    override fun onBackPressed() {
//        super.onBackPressed()
//        finishAffinity()
//        System.exit(0)
//    }
//
//    companion object {
//        /**
//         * Id to identity READ_CONTACTS permission request.
//         */
//        private const val REQUEST_READ_CONTACTS = 0
//
//    }
//}
