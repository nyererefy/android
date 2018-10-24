package com.konektedi.vs.student;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.konektedi.vs.R;
import com.konektedi.vs.utilities.api.ApiUtilities;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.student.UserPreferencesKt.grabPreference;
import static com.konektedi.vs.student.UserPreferencesKt.savePreference;
import static com.konektedi.vs.utilities.Constants.USERNAME;

public class Settings extends AppCompatActivity {
    Button changeUsernameBtn, changePasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeUsernameBtn = findViewById(R.id.changeUsernameBtn);
        changePasswordBtn = findViewById(R.id.changePasswordBtn);

        changeUsernameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeUsername();
            }
        });

        changePasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    private void changeUsername() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.v_change_username, null);
        builder.setView(dialogView);

        final EditText newUsername;
        final ProgressBar progressBar;
        Button changeBtn;

        progressBar = dialogView.findViewById(R.id.progressBar);
        changeBtn = dialogView.findViewById(R.id.changeBtn);
        newUsername = dialogView.findViewById(R.id.newUsername);

        newUsername.setText(grabPreference(Settings.this, USERNAME));

        final AlertDialog dialog = builder.create();

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String new_username = newUsername.getText().toString().trim();
                View focusView = null;
                boolean cancel = false;

                if (TextUtils.isEmpty(new_username)) {
                    newUsername.setError(getString(R.string.error_field_required));
                    focusView = newUsername;
                    cancel = true;
                } else if (new_username.length() < 3 || new_username.length() > 16) {
                    newUsername.setError(getString(R.string.invalid_username));
                    focusView = newUsername;
                    cancel = true;
                } else if (new_username.equals(grabPreference(Settings.this, USERNAME))) {
                    newUsername.setError(getString(R.string.same_username));
                    focusView = newUsername;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Map<String, String> map = new HashMap<>();

                    map.put("username", new_username);

                    Call<ResponseBody> call = ApiUtilities.getClient().changeUsername(map);

                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (response.isSuccessful()) {
                                dialog.cancel();
                                Toast.makeText(Settings.this, R.string.username_changed, Toast.LENGTH_LONG).show();
                                savePreference(Settings.this, USERNAME, new_username);
                            } else if (response.code() == 404) {
                                newUsername.setError(getString(R.string.username_taken));
                                newUsername.requestFocus();
                            } else {
                                Toast.makeText(Settings.this, R.string.error_occurred, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                            dialog.cancel();
                            Toast.makeText(Settings.this, R.string.error_occurred, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        dialog.show();
    }

    private void changePassword() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.v_change_password, null);
        builder.setView(dialogView);
        //ids
        final EditText currentPassword, newPassword, confirmPassword;
        final ProgressBar progressBar;
        Button changeBtn = dialogView.findViewById(R.id.changeBtn);
        currentPassword = dialogView.findViewById(R.id.currentPassword);
        newPassword = dialogView.findViewById(R.id.newPassword);
        confirmPassword = dialogView.findViewById(R.id.confirmPassword);
        progressBar = dialogView.findViewById(R.id.progressBar);

        final AlertDialog dialog = builder.create();

        //listeners
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View focusView = null;
                boolean cancel = false;

                String current_password = currentPassword.getText().toString().trim();
                String new_password = newPassword.getText().toString().trim();
                String confirm_password = confirmPassword.getText().toString().trim();

                if (TextUtils.isEmpty(current_password)) {
                    currentPassword.setError(getString(R.string.error_field_required));
                    focusView = currentPassword;
                    cancel = true;
                } else if (current_password.length() < 6) {
                    currentPassword.setError(getString(R.string.error_invalid_password));
                    focusView = currentPassword;
                    cancel = true;
                } else if (TextUtils.isEmpty(new_password)) {
                    newPassword.setError(getString(R.string.error_field_required));
                    focusView = newPassword;
                    cancel = true;
                } else if (new_password.length() < 6 || new_password.length() > 10) {
                    newPassword.setError(getString(R.string.error_invalid_password));
                    focusView = newPassword;
                    cancel = true;
                } else if (new_password.equals(current_password)) {
                    newPassword.setError(getString(R.string.password_should_not_match));
                    focusView = newPassword;
                    cancel = true;
                } else if (!new_password.equals(confirm_password)) {
                    confirmPassword.setError(getString(R.string.password_not_match));
                    focusView = confirmPassword;
                    cancel = true;
                }

                if (cancel) {
                    focusView.requestFocus();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    Map<String, String> map = new HashMap<>();

                    map.put("password", current_password);
                    map.put("new_password", new_password);

                    Call<ResponseBody> call = ApiUtilities.getClient().changePassword(map);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            progressBar.setVisibility(View.INVISIBLE);
                            if (response.isSuccessful()) {
                                dialog.cancel();
                                Toast.makeText(Settings.this, R.string.password_changed, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(Settings.this, R.string.wrong_password, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            progressBar.setVisibility(View.INVISIBLE);
                            dialog.cancel();
                            Toast.makeText(Settings.this, R.string.error_occurred, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
        dialog.show();
    }

}
