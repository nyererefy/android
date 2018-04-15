package com.konektedi.vs.Home.Elections;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.konektedi.vs.R;

import java.util.List;

public class Elections extends Fragment {

    RecyclerView recyclerView;
    ElectionsAdapter electionsAdapter;
    ProgressBar progressBar;
    ElectionsViewModel electionsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.elections_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        electionsViewModel = ViewModelProviders.of(getActivity()).get(ElectionsViewModel.class);

        electionsViewModel.getAllElections().observe(this, new Observer<List<ElectionsModel>>() {
            @Override
            public void onChanged(@Nullable List<ElectionsModel> electionsModels) {

                electionsAdapter = new ElectionsAdapter(getActivity(), electionsModels);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(electionsAdapter);
            }
        });


//        progressBar.setVisibility(View.VISIBLE);

        return rootView;
    }


//    private void getElections() {
//
//        Call<List<ElectionsModel>> call = ApiUtilities.getElections().getElections();
//
//        call.enqueue(new Callback<List<ElectionsModel>>() {
//            @Override
//            public void onResponse(Call<List<ElectionsModel>> call, Response<List<ElectionsModel>> response) {
//                List<ElectionsModel> electionsModelList = response.body();
//
//                progressBar.setVisibility(View.GONE);
//
//                electionsAdapter = new ElectionsAdapter(getActivity(), electionsModelList);
//
//                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//                recyclerView.setAdapter(electionsAdapter);
//
//            }
//
//            @Override
//            public void onFailure(Call<List<ElectionsModel>> call, Throwable t) {
//                progressBar.setVisibility(View.GONE);
//                Toast.makeText(getContext(), "Retro failed", Toast.LENGTH_LONG).show();
//            }
//        });
//
//
//    }

}