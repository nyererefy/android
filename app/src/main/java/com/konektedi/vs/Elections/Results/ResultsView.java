package com.konektedi.vs.Elections.Results;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.konektedi.vs.Elections.CategoriesAdapter;
import com.konektedi.vs.Elections.CategoriesModel;
import com.konektedi.vs.R;

import java.util.ArrayList;
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
        getCategories();
    }

    private void getCategories() {

        categoriesModelList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            CategoriesModel category = new CategoriesModel();

            category.setCategory_id(i);
            category.setCategory("President " + i);

            categoriesModelList.add(category);
        }

        categoriesGridView.setAdapter(new CategoriesAdapter(getApplication(), categoriesModelList));

    }

}
