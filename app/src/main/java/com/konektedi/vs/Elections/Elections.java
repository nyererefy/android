package com.konektedi.vs.Elections;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.konektedi.vs.R;

import java.util.ArrayList;
import java.util.List;

public class Elections extends Fragment {

    RecyclerView recyclerView;
    ElectionsAdapter electionsAdapter;
    List<ElectionsModel> electionsModelList;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.elections_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);


        getElections();
    }

    private void getElections() {

        electionsModelList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            ElectionsModel election = new ElectionsModel();

            election.setElection_id(i);
            election.setElection("CUHAS ORGANIZATIONS GENERAL ELECTION 202" + i);

            electionsModelList.add(election);
        }

        electionsAdapter = new ElectionsAdapter(getActivity(), electionsModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(electionsAdapter);

    }


}