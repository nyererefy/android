package com.konektedi.vs.Home.Candidates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.konektedi.vs.R;

public class Profile extends AppCompatActivity {
    ImageView cover;
    TextView nameView, schoolView, discriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_profile_activity);

        nameView = findViewById(R.id.nameView);
        schoolView = findViewById(R.id.schoolView);
        discriptionView = findViewById(R.id.discriptionView);
        cover = findViewById(R.id.cover);

        showDetails();
    }

    protected void showDetails(){
        Bundle data = getIntent().getExtras();

        String name = data.getString("NAME");
        String school = data.getString("SCHOOL");
        String discription = data.getString("discription");

        nameView.setText(name);
        schoolView.setText(school);
        discriptionView.setText(discription);

    }
}
