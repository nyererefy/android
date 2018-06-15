package com.konektedi.vs.home.elections;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.konektedi.vs.R;

public class ElectionsFragment extends Fragment {

    RecyclerView recyclerView;
    ElectionsAdapter electionsAdapter;
    ElectionsViewModel viewModel;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeToRefresh;

    public ElectionsFragment() {
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
        swipeToRefresh = rootView.findViewById(R.id.swipeToRefresh);

        swipeToRefresh.setRefreshing(true);
        swipeToRefresh.setOnRefreshListener(this::getElections);

        return rootView;
    }

    private void getElections() {

        viewModel = ViewModelProviders.of(getActivity()).get(ElectionsViewModel.class);

        viewModel.getNetworkStatus().observe(this, networkStatus -> {
            if (networkStatus != null) {
                switch (networkStatus) {
                    case LOADING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case LOADED:
                        progressBar.setVisibility(View.GONE);
                        break;
                    case ERROR:
                        Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();
                        break;
                    case FAILED:
                        Toast.makeText(getActivity(), R.string.failed_connect, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        viewModel.getAllElections().observe(this, electionsModels -> {

            electionsAdapter = new ElectionsAdapter(getActivity(), electionsModels);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(electionsAdapter);
        });

        swipeToRefresh.setRefreshing(false);
    }

}