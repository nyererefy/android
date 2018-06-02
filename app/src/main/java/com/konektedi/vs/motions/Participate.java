package com.konektedi.vs.motions;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.konektedi.vs.R;
import com.konektedi.vs.student.StudentPreferences;
import com.konektedi.vs.utilities.Api.ApiUtilities;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.utilities.Constants.ID;
import static com.konektedi.vs.utilities.Constants.RANK;
import static com.konektedi.vs.utilities.Constants.UNIVERSITY;

public class Participate extends AppCompatActivity {
    Button submitBtn;
    EditText opinionInput;
    TextView motion, title;
    RadioGroup radioGroup;
    RadioButton noRadioButton, yesRadioButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participate_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        submitBtn = findViewById(R.id.submitBtn);
        opinionInput = findViewById(R.id.opinionInput);
        title = findViewById(R.id.title);
        motion = findViewById(R.id.motion);
        radioGroup = findViewById(R.id.radioGroup);
        noRadioButton = findViewById(R.id.noRadioButton);
        yesRadioButton = findViewById(R.id.yesRadioButton);
        progressBar = findViewById(R.id.progressBar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showMotion();

    }

    protected void showMotion() {
        Bundle data = getIntent().getExtras();

        assert data != null;
        String motionTexts = data.getString("motion");
        String titleTexts = data.getString("title");
        final String motion_id = data.getString("motion_id");

        setTitle(R.string.motion);
        title.setText(titleTexts);
        motion.setText(motionTexts);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitOpinion(motion_id);
            }
        });
    }

    private void submitOpinion(String motion_id) {
        showProgressBar();
        hideKeyboard();

        String opinion = opinionInput.getText().toString();
        String vote = null;

        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == yesRadioButton.getId()) {
            vote = "yes";

        } else if (selectedId == noRadioButton.getId()) {
            vote = "no";
        }

        Map<String, String> map = new HashMap<>();

        map.put("id", StudentPreferences.getPreference(this, ID));
        map.put("opinion", opinion);
        map.put("motion_id", motion_id);
        map.put("university_id", StudentPreferences.getPreference(this, UNIVERSITY));
        map.put("rank", RANK);
        map.put("vote", vote);

        Call<ResponseBody> call = ApiUtilities.getClient().postOpinion(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgressBar();
                if (response.isSuccessful()) {
                    Toast.makeText(Participate.this, "success", Toast.LENGTH_LONG).show();
                    goBack();
                } else {
                    Toast.makeText(Participate.this, R.string.error_occurred, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(Participate.this, R.string.error_occurred, Toast.LENGTH_LONG).show();
            }
        });

    }

    public void goBack() {
        super.onBackPressed();
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
