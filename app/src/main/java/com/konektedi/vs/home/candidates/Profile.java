package com.konektedi.vs.home.candidates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.konektedi.vs.R;

import static com.konektedi.vs.utilities.Constants.CLASS_NAME;
import static com.konektedi.vs.utilities.Constants.COVER;
import static com.konektedi.vs.utilities.Constants.DESCRIPTION;
import static com.konektedi.vs.utilities.Constants.NAME;

public class Profile extends AppCompatActivity {
    ImageView cover;
    TextView nameView, schoolView, discriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidate_profile_activity);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        schoolView = findViewById(R.id.schoolView);
        discriptionView = findViewById(R.id.discriptionView);
        cover = findViewById(R.id.cover);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showDetails();
    }

    protected void showDetails() {
        Bundle data = getIntent().getExtras();

        String coverURL = data.getString(COVER);
        String name = data.getString(NAME);
        String class_name = data.getString(CLASS_NAME);
        String description = data.getString(DESCRIPTION);

        setTitle(name);
        schoolView.setText(class_name);
        discriptionView.setText(description);

        Glide.with(this)
                .load(coverURL)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(cover);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }
}
