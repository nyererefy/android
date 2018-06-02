package com.konektedi.vs.home.elections;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.konektedi.vs.R;
import com.konektedi.vs.utilities.NetworkState;
import com.konektedi.vs.utilities.NetworkStatus;

import java.util.List;

public class Elections extends Fragment {

    RecyclerView recyclerView;
    ElectionsAdapter electionsAdapter;
    ElectionsViewModel electionsViewModel;
    NetworkState networkState;
    ProgressBar progressBar;

    public Elections() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getElections();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.elections_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    private void getElections() {

        if (networkState != null && networkState.getStatus() == NetworkStatus.RUNNING) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }

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

}