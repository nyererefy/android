package com.konektedi.vs.student;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.konektedi.vs.MainActivity;
import com.konektedi.vs.R;
import com.konektedi.vs.other.SupportActivityMain;
import com.konektedi.vs.utilities.Api.Api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.konektedi.vs.utilities.Api.ApiUtilities.BASE_URL;
import static com.konektedi.vs.utilities.Constants.ID;
import static com.konektedi.vs.utilities.Constants.IS_LOGGED_IN;
import static com.konektedi.vs.utilities.Constants.NAME;
import static com.konektedi.vs.utilities.Constants.REG;
import static com.konektedi.vs.utilities.Constants.RESIDENCE;
import static com.konektedi.vs.utilities.Constants.SCHOOL;
import static com.konektedi.vs.utilities.Constants.SCHOOL_NAME;
import static com.konektedi.vs.utilities.Constants.SEX;
import static com.konektedi.vs.utilities.Constants.UNIVERSITY;
import static com.konektedi.vs.utilities.Constants.USERNAME;
import static com.konektedi.vs.utilities.Constants.VSPreferences;
import static com.konektedi.vs.utilities.Constants.YEAR;

/**
 * A login screen that offers login via reg_no/password.
 */
public class Login extends AppCompatActivity {

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mPasswordView, mReg_noView;
    private View mProgressView;
    private View mLoginFormView;
    TextView wrongPasswordOrRegView, supportBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mReg_noView = findViewById(R.id.reg_no);
        mPasswordView = findViewById(R.id.password);
        wrongPasswordOrRegView = findViewById(R.id.wrongPasswordOrRegView);
        supportBtn = findViewById(R.id.supportBtn);

        supportBtn.setOnClickListener(view -> startActivity(new Intent(Login.this, SupportActivityMain.class)));

        Button mReg_noSignInButton = findViewById(R.id.reg_no_sign_in_button);
        mReg_noSignInButton.setOnClickListener(view -> attemptLogin());

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);

        Boolean is_logged_in = StudentPreferences.getLoginPreference(this);

        if (is_logged_in) {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid reg_no, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mReg_noView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String reg_no = mReg_noView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid reg_no address.
        if (TextUtils.isEmpty(reg_no)) {
            mReg_noView.setError(getString(R.string.error_field_required));
            focusView = mReg_noView;
            cancel = true;
        } else if (!isReg_noValid(reg_no)) {
            mReg_noView.setError(getString(R.string.error_invalid_reg_no));
            focusView = mReg_noView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(reg_no, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isReg_noValid(String reg_no) {
        //TODO: Replace this with your own logic
        return reg_no.length() > 6;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() >= 6;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        SharedPreferences preferences = getSharedPreferences(VSPreferences, MODE_PRIVATE);


        private final String mReg_no;
        private final String mPassword;
        private StudentModel studentModel;
        private String errors;

        UserLoginTask(String reg_no, String password) {
            mReg_no = reg_no;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            Map<String, String> map = new HashMap<>();

            map.put("reg_no", mReg_no);
            map.put("password", mPassword);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Api api = retrofit.create(Api.class);

            Call<StudentModel> call = api.authenticate(map);

            try {
                Response<StudentModel> response = call.execute();

                if (response.isSuccessful()) {
                    studentModel = response.body();
                    return true;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                String name = studentModel.getName();

                if (studentModel.getActivated().equals("0")) {
                    alertWarning(name, R.string.alert_account_not_activated);
                } else if (studentModel.getState().equals("0")) {
                    alertWarning(name, R.string.alert_account_disabled);
                } else if (studentModel.getVerified().equals("0")) {
                    alertWarning(name, R.string.alert_account_not_verified);
                } else if (studentModel.getApproved().equals("0")) {
                    alertWarning(name, R.string.alert_account_not_approved);
                } else {
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.clear();

                    editor.putString(ID, studentModel.getId());
                    editor.putString(USERNAME, studentModel.getUsername());
                    editor.putString(NAME, studentModel.getName());
                    editor.putString(REG, studentModel.getReg_no());
                    editor.putString(UNIVERSITY, studentModel.getUniversity_id());
                    editor.putString(SCHOOL, studentModel.getSchool_id());
                    editor.putString(YEAR, studentModel.getYear());
                    editor.putString(SEX, studentModel.getSex());
                    editor.putString(RESIDENCE, studentModel.getResidence_id());
                    editor.putString(SCHOOL_NAME, studentModel.getSchool_name());
                    editor.putBoolean(IS_LOGGED_IN, true);
                    editor.apply();
                    goToMainActivity();
                    finish();
                }

            } else {
                wrongPasswordOrRegView.setVisibility(View.VISIBLE);
                wrongPasswordOrRegView.setText(R.string.wrong_password_or_reg);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }

        private void goToMainActivity() {
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        System.exit(0);
    }

    void alertWarning(String name, int message) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(name);
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setNeutralButton("Ok", (dialog, which) -> {
            finishAffinity();
            System.exit(0);
        });
        builder.show();
    }
}

