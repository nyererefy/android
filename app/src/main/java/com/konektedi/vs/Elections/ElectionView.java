package com.konektedi.vs.Elections;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.konektedi.vs.Elections.Results.ResultsView;
import com.konektedi.vs.R;

import java.util.ArrayList;
import java.util.List;

public class ElectionView extends AppCompatActivity {
    List<CategoriesModel> categoriesModelList;
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
                Toast.makeText(ElectionView.this, "Live Votes",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCategories() {

        categoriesModelList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            CategoriesModel category = new CategoriesModel();

            category.setCategory_id(i);
            category.setCategory("President category " + i);

            categoriesModelList.add(category);
        }

        categoriesGridView.setAdapter(new CategoriesAdapter(getApplication(), categoriesModelList));

    }


}
