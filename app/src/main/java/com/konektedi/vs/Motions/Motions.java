package com.konektedi.vs.Motions;

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

/**
 * Created by Sy on 3/28/2018.
 */

public class Motions extends Fragment {
    MotionsAdapter motionsAdapter;
    List<MotionsModel> motionsModelList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.motions_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);


        getCategories();
    }

    private void getCategories() {

        motionsModelList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            MotionsModel p = new MotionsModel();

            p.setMotion_id(i);
            p.setTitle("Tuongeze boom au? mara " + i);

            motionsModelList.add(p);
        }

        motionsAdapter = new MotionsAdapter(getActivity(), motionsModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(motionsAdapter);

    }

}
