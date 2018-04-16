package com.konektedi.vs.Home.Elections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.konektedi.vs.Home.Categories.CategoriesAdapter;
import com.konektedi.vs.Home.Categories.CategoriesModel;
import com.konektedi.vs.Home.Results.ResultsView;
import com.konektedi.vs.Login.LoginActivity;
import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectionView extends AppCompatActivity {
    GridView categoriesGridView;
    Button resultsViewBtn, reviewsViewBtn;

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
                Intent intent = new Intent(ElectionView.this, LoginActivity.class);
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

        Call<List<CategoriesModel>> call = ApiUtilities.getCategories().getCategories(election_id);

        call.enqueue(new Callback<List<CategoriesModel>>() {
            @Override
            public void onResponse(Call<List<CategoriesModel>> call, Response<List<CategoriesModel>> response) {

                List<CategoriesModel> categoriesModelList = response.body();
                categoriesGridView.setAdapter(new CategoriesAdapter(getApplication(), categoriesModelList));
            }

            @Override
            public void onFailure(Call<List<CategoriesModel>> call, Throwable t) {

            }
        });


    }


}
