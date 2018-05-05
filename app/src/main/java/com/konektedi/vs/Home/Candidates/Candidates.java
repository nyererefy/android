package com.konektedi.vs.Home.Candidates;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.konektedi.vs.R;

import java.util.List;

public class Candidates extends AppCompatActivity {
    RecyclerView recyclerView;
    CandidatesAdapter adapter;
    CandidatesViewModel candidatesViewModel;
    ProgressBar progressBar;
    CardView cardView;
    TextView alertTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        cardView = findViewById(R.id.cardView);
        alertTextView = findViewById(R.id.alertTextView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();

        String election_id = data != null ? data.getString("election_id") : null;
        String category_id = data != null ? data.getString("category_id") : null;
        String category;
        if (data != null) {
            category = data.getString("category");
            setTitle(category);
        }

        candidatesViewModel = ViewModelProviders.of(this).get(CandidatesViewModel.class);
        candidatesViewModel.getAllCandidates(election_id, category_id).observe(this,
                new Observer<List<CandidatesModel>>() {
                    @Override
                    public void onChanged(@Nullable List<CandidatesModel> candidatesModels) {
                        if (candidatesModels != null && !candidatesModels.isEmpty()) {

                            hideProgressBar();

                            int numberOfColumns = 2;
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), numberOfColumns));
                            adapter = new CandidatesAdapter(Candidates.this, candidatesModels);
                            recyclerView.setAdapter(adapter);

                        } else {
                            hideProgressBar();
                            showAlert(R.string.no_candidates);
                        }

                    }
                });
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void showAlert(int alertText) {
        cardView.setVisibility(View.VISIBLE);
        alertTextView.setText(alertText);
    }

    public void finishVote() {
        super.onBackPressed();
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
