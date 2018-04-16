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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        electionsViewModel = ViewModelProviders.of(getActivity()).get(ElectionsViewModel.class);

        electionsViewModel.getAllElections().observe(this, new Observer<List<ElectionsModel>>() {
            @Override
            public void onChanged(@Nullable List<ElectionsModel> electionsModels) {

                electionsAdapter = new ElectionsAdapter(getActivity(), electionsModels);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(electionsAdapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.elections_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }


}