package com.konektedi.vs.News;

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

import java.util.List;

/**
 * Created by Sy on 3/28/2018.
 */

public class News extends Fragment {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    NewsViewModel viewModel;
    NewsAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);

        viewModel.getAllNews().observe(this, new Observer<List<NewsModel>>() {
            @Override
            public void onChanged(@Nullable List<NewsModel> newsModels) {
                hideProgressBar();

                adapter = new NewsAdapter(getActivity(), newsModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(adapter);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.news_fragment, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        progressBar = rootView.findViewById(R.id.progressBar);

        return rootView;
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

}
