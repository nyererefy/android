package com.konektedi.vs.Motions.Opinions;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        Call<List<OpinionsModel>> call = ApiUtilities.getOpinions().getOpinions(motion_id);
        call.enqueue(new Callback<List<OpinionsModel>>() {
            @Override
            public void onResponse(Call<List<OpinionsModel>> call, Response<List<OpinionsModel>> response) {

                progressBar.setVisibility(View.GONE);

                List<OpinionsModel> opinionsModelList = response.body();
                adapter = new OpinionsAdapter(getApplicationContext(), opinionsModelList);

                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<OpinionsModel>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(Opinions.this, "Error occurred", Toast.LENGTH_SHORT).show();

            }
        });
    }


}
