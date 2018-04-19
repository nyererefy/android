package com.konektedi.vs.Home.Candidates;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.konektedi.vs.R;

import java.util.List;

public class Candidates extends AppCompatActivity {
    RecyclerView recyclerView;
    CandidatesAdapter adapter;
    CandidatesViewModel candidatesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle data = getIntent().getExtras();

        String election_id = data != null ? data.getString("election_id") : null;
        String category_id = data != null ? data.getString("category_id") : null;
        String category;
        if (data != null) {
            category = data.getString("category");
            setTitle(category);
        }

//        Call<List<CandidatesModel>> call = ApiUtilities.getCandidates().getCandidates(election_id, category_id);
//        call.enqueue(new Callback<List<CandidatesModel>>() {
//            @Override
//            public void onResponse(Call<List<CandidatesModel>> call, Response<List<CandidatesModel>> response) {
//                List<CandidatesModel> candidatesModelList = response.body();
//
//                int numberOfColumns = 2;
//                recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), numberOfColumns));
//                adapter = new CandidatesAdapter(Candidates.this, candidatesModelList);
//                recyclerView.setAdapter(adapter);
//            }
//
//            @Override
//            public void onFailure(Call<List<CandidatesModel>> call, Throwable t) {
//                Toast.makeText(getApplication(), "Error", Toast.LENGTH_SHORT).show();
//            }
//        });

        //Better way
        candidatesViewModel = ViewModelProviders.of(this).get(CandidatesViewModel.class);
        candidatesViewModel.getAllCandidates(election_id, category_id).observe(this,
                new Observer<List<CandidatesModel>>() {
                    @Override
                    public void onChanged(@Nullable List<CandidatesModel> candidatesModels) {
                        if (candidatesModels != null) {
                            int numberOfColumns = 2;
                            recyclerView.setLayoutManager(new GridLayoutManager(getApplication(), numberOfColumns));
                            adapter = new CandidatesAdapter(Candidates.this, candidatesModels);
                            recyclerView.setAdapter(adapter);
                        } else {
                            Toast.makeText(Candidates.this, "Posts not retrieved", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}
