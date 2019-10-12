package com.nyererefy.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.nyererefy.R
import com.nyererefy.utilities.Pref
import com.nyererefy.utilities.common.Constants.ID
import com.nyererefy.utilities.common.Constants.NAME
import com.nyererefy.utilities.common.Constants.USERNAME
import com.nyererefy.viewmodels.LoginViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.clearTop
import org.jetbrains.anko.design.longSnackbar
import org.jetbrains.anko.intentFor
import timber.log.Timber
import javax.inject.Inject


class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val loginViewModel: LoginViewModel by viewModels { viewModelFactory }
    private lateinit var pref: Pref

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        AndroidInjection.inject(this)

        pref = Pref(this)

        setUpGoogleLogin()
    }

    private fun setUpGoogleLogin() {
        // Configure sign-in to requests the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail() //so that _token generated will have email scope
                .build()

        loginWithGoogle.setOnClickListener {
            // Build a GoogleSignInClient with the options specified by gso.
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // This task is always completed immediately, there is no need to attach an
            // asynchronous listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleGoogleSignInResult(task)
        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val idToken = account?.idToken

            Timber.d("Token: $idToken")

            idToken?.let { loginViewModel.setToken(it) }

            //User data
            loginViewModel.data.observe(this, Observer {
                Timber.d("data: $it")

                val editor = pref.sharedPref.edit()

                editor.putString(ID, it.login().id())
                editor.putString(NAME, it.login().name())
                editor.putString(USERNAME, it.login().username())
                editor.apply()

                startActivity(intentFor<MainActivity>().clearTop())
            })
        } catch (e: ApiException) {
            Timber.e(e, "handleSignInResult:error")
            container.longSnackbar(getString(R.string.error))
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9002
    }
}