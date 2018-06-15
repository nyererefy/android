package com.konektedi.vs.motions;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
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

/**
 * Created by Sy on 3/28/2018.
 */

public class MotionsFragment extends Fragment {

    MotionsAdapter motionsAdapter;
    RecyclerView recyclerView;
    MotionsViewModel viewModel;
    ProgressBar progressBar;
    SwipeRefreshLayout swipeToRefresh;


    public MotionsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getMotions();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.motions_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);
        swipeToRefresh = rootView.findViewById(R.id.swipeToRefresh);

        return rootView;
    }

    private void getMotions() {
        viewModel = ViewModelProviders.of(getActivity()).get(MotionsViewModel.class);

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

        viewModel.getAllMotions().observe(this, motionsModels -> {

            motionsAdapter = new MotionsAdapter(getActivity(), motionsModels);

            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(motionsAdapter);
        });
    }

}
