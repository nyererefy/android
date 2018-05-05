package com.konektedi.vs.Home.Elections;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;

import com.konektedi.vs.Home.Categories.CategoriesAdapter;
import com.konektedi.vs.Home.Categories.CategoriesModel;
import com.konektedi.vs.Home.Categories.CategoriesViewModel;
import com.konektedi.vs.Home.Results.ResultsView;
import com.konektedi.vs.R;
import com.konektedi.vs.Student.Login;

import java.util.List;

public class ElectionView extends AppCompatActivity {
    GridView categoriesGridView;
    Button resultsViewBtn, reviewsViewBtn;
    CategoriesViewModel model;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.election_view_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        categoriesGridView = findViewById(R.id.categoriesGridView);
        resultsViewBtn = findViewById(R.id.resultsViewBtn);
        reviewsViewBtn = findViewById(R.id.reviewsViewBtn);
        progressBar = findViewById(R.id.progressBar);

        getCategories();

        resultsViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElectionView.this, ResultsView.class);
                startActivity(intent);
            }
        });

        reviewsViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ElectionView.this, Login.class);
                startActivity(intent);
            }
        });

    }

    private void getCategories() {

        Bundle data = getIntent().getExtras();

        String election_id = "";
        String election_title = "";

        if (data != null) {
            election_id = data.getString("election_id");
            election_title = data.getString("election_title");
        }

        setTitle(election_title);

        model = ViewModelProviders.of(this).get(CategoriesViewModel.class);
        model.getAllCategories(election_id).observe(this, new Observer<List<CategoriesModel>>() {
            @Override
            public void onChanged(@Nullable List<CategoriesModel> categoriesModels) {
                hideProgressBar();
                categoriesGridView.setAdapter(new CategoriesAdapter(getApplication(), categoriesModels));

            }
        });

    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
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
