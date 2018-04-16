package com.konektedi.vs.Motions;

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

/**
 * Created by Sy on 3/28/2018.
 */

public class Motions extends Fragment {
    MotionsAdapter motionsAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    MotionsViewModel motionsViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        motionsViewModel = ViewModelProviders.of(getActivity()).get(MotionsViewModel.class);
        motionsViewModel.getAllMotions().observe(this, new Observer<List<MotionsModel>>() {
            @Override
            public void onChanged(@Nullable List<MotionsModel> motionsModels) {

                motionsAdapter = new MotionsAdapter(getActivity(), motionsModels);

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(motionsAdapter);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.motions_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }


}
