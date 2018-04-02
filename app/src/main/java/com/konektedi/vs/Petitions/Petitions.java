package com.konektedi.vs.Petitions;

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

public class Petitions extends Fragment {
    PetitionsAdapter petitionsAdapter;
    List<PetitionsModel> petitionsModelList;
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.petitions_fragment, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);


        getCategories();
    }

    private void getCategories() {

        petitionsModelList = new ArrayList<>();

        for (int i = 0; i < 6; i++) {

            PetitionsModel p = new PetitionsModel();

            p.setPetition_id(i);
            p.setTitle("Tuongeze boom au? mara " + i);

            petitionsModelList.add(p);
        }

        petitionsAdapter = new PetitionsAdapter(getActivity(), petitionsModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(petitionsAdapter);

    }

}
