package com.konektedi.vs.student;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.konektedi.vs.R;

import static com.konektedi.vs.student.UserPreferencesKt.grabPreference;
import static com.konektedi.vs.utilities.Constants.NAME;

public class StudentProfile extends AppCompatActivity {

    TextView nameView, usernameView, schoolView, sexView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(grabPreference(this, NAME));

//        sexView = findViewById(R.id.sexView);
//        schoolView = findViewById(R.id.schoolView);
//        usernameView = findViewById(R.id.usernameView);
//        usernameView = findViewById(R.id.usernameView);
//        nameView = findViewById(R.id.nameView);

//        showDetails();
    }

//    protected void showDetails() {
//        nameView.setText(UserPreferences.getPreference(this, NAME));
//
//        String username = "@" + UserPreferences.getPreference(this, USERNAME);
//        usernameView.setText(username);
//
//        String classIn = UserPreferences.getPreference(this, SCHOOL_NAME) + " " + UserPreferences.getPreference(this, YEAR);
//        schoolView.setText(classIn);
//
//        String sex;
//        if (UserPreferences.getPreference(this, SEX).equals("M"))
//            sex = "Male";
//        else sex = "Female";
//        sexView.setText(sex);
//    }

}
