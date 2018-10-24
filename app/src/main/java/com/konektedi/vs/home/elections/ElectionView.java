package com.konektedi.vs.home.elections;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.konektedi.vs.R;
import com.konektedi.vs.home.categories.CategoriesAdapter;
import com.konektedi.vs.home.categories.CategoriesViewModel;
import com.konektedi.vs.home.results.ResultsView;
import com.konektedi.vs.home.reviews.ReviewsActivity;

public class ElectionView extends AppCompatActivity {
    GridView categoriesGridView;
    Button resultsViewBtn, reviewsViewBtn;
    CategoriesViewModel viewModel;
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

        resultsViewBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ElectionView.this, ResultsView.class);
            startActivity(intent);
        });

        reviewsViewBtn.setOnClickListener(view -> {
            Intent intent = new Intent(ElectionView.this, ReviewsActivity.class);
            startActivity(intent);
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

        viewModel = ViewModelProviders.of(this).get(CategoriesViewModel.class);

//        viewModel.getNetworkState().observe(this, networkStatus -> {
//            if (networkStatus != null) {
//                switch (networkStatus) {
//                    case networkStatus.:
//                        showProgressBar();
//                        break;
//                    case LOADED:
//                        hideProgressBar();
//                        break;
//                    case ERROR:
//                        hideProgressBar();
//                        Toast.makeText(this, R.string.error, Toast.LENGTH_SHORT).show();
//                        break;
//                    case FAILED:
//                        hideProgressBar();
//                        Toast.makeText(this, R.string.failed_connect, Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        });

        viewModel.getCategories(1).observe(this, categoriesModels -> {
            categoriesGridView.setAdapter(new CategoriesAdapter(ElectionView.this, categoriesModels));
        });
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

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }
}
