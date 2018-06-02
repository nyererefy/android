package com.konektedi.vs.home.results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.konektedi.vs.home.categories.CategoriesModel;
import com.konektedi.vs.R;

import java.util.List;

public class ResultsView extends AppCompatActivity {
    List<CategoriesModel> categoriesModelList;
    GridView categoriesGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_view_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoriesGridView = findViewById(R.id.categoriesGridView);
    }



}
