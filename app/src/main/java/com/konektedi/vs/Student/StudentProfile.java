package com.konektedi.vs.Student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.konektedi.vs.R;

import static com.konektedi.vs.Utilities.Constants.NAME;
import static com.konektedi.vs.Utilities.Constants.SCHOOL_NAME;
import static com.konektedi.vs.Utilities.Constants.SEX;
import static com.konektedi.vs.Utilities.Constants.USERNAME;
import static com.konektedi.vs.Utilities.Constants.YEAR;

public class StudentProfile extends AppCompatActivity {

    TextView nameView, usernameView, schoolView, sexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sexView = findViewById(R.id.sexView);
        schoolView = findViewById(R.id.schoolView);
        usernameView = findViewById(R.id.usernameView);
        usernameView = findViewById(R.id.usernameView);
        nameView = findViewById(R.id.nameView);

        showDetails();
    }

    protected void showDetails() {
        nameView.setText(StudentPreferences.getPreference(this, NAME));

        String username = "@" + StudentPreferences.getPreference(this, USERNAME);
        usernameView.setText(username);

        String classIn = StudentPreferences.getPreference(this, SCHOOL_NAME) + " " + StudentPreferences.getPreference(this, YEAR);
        schoolView.setText(classIn);

        String sex;
        if (StudentPreferences.getPreference(this, SEX).equals("M"))
            sex = "Male";
        else sex = "Female";
        sexView.setText(sex);
    }

}
