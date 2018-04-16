package com.konektedi.vs.Home.Candidates;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Candidates extends AppCompatActivity {
    RecyclerView recyclerView;
    CandidatesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getCandidates();
    }


    protected void getCandidates() {
        Bundle data = getIntent().getExtras();

        String category_id = data != null ? data.getString("category_id") : null;
        String election_id = data != null ? data.getString("election_id") : null;
        String category;
        if (data != null) {
            category = data.getString("category");
            setTitle(category);
        }

        Call<List<CandidatesModel>> call = ApiUtilities.getCandidates().getCandidates(election_id, category_id);
        call.enqueue(new Callback<List<CandidatesModel>>() {
            @Override
            public void onResponse(Call<List<CandidatesModel>> call, Response<List<CandidatesModel>> response) {
                List<CandidatesModel> candidatesModelList = response.body();

                int numberOfColumns = 2;
                recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), numberOfColumns));
                adapter = new CandidatesAdapter(getApplicationContext(), candidatesModelList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<CandidatesModel>> call, Throwable t) {
                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
