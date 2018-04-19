package com.konektedi.vs.Motions.Opinions;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ProgressBar;

import com.konektedi.vs.R;

import java.util.List;

public class Opinions extends AppCompatActivity {

    OpinionsModelView modelView;
    OpinionsAdapter adapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opinions_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        getOpinions();
    }

    protected void getOpinions() {
        Bundle data = getIntent().getExtras();

        assert data != null;
        String motion_id = data.getString("motion_id");
        String title = data.getString("title");

        setTitle("Opinions: " + title);

        modelView = ViewModelProviders.of(this).get(OpinionsModelView.class);
        modelView.getAllOpinions(motion_id).observe(this, new Observer<List<OpinionsModel>>() {
            @Override
            public void onChanged(@Nullable List<OpinionsModel> opinionsModels) {
                adapter = new OpinionsAdapter(getApplicationContext(), opinionsModels);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }
        });

    }

}
