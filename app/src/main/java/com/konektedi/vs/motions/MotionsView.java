package com.konektedi.vs.motions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.konektedi.vs.R;

public class MotionsView extends AppCompatActivity {

    TextView title, motion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.motion_view_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        motion = findViewById(R.id.motion);
        title = findViewById(R.id.title);

        showMotion();
    }

    protected void showMotion() {
        Bundle data = getIntent().getExtras();

        assert data != null;
        String motionTexts = data.getString("motion");
        String titleTexts = data.getString("title");

        setTitle(R.string.motion);
        title.setText(titleTexts);
        motion.setText(motionTexts);

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
