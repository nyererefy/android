package com.konektedi.vs.Motions.Opinions;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.ListItemClickListener;

public class OpinionsActivity extends AppCompatActivity implements ListItemClickListener {
    private String TAG = "OpinionsActivity";
    OpinionsViewModel viewModel;
    RecyclerView recyclerView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.opinions_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        getOpinions();
    }

    protected void getOpinions() {
        showProgressBar();
        Bundle data = getIntent().getExtras();

        assert data != null;
        int motion_id = data.getInt("motion_id");
        String title = data.getString("title");

        setTitle("Opinions: " + title);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(OpinionsViewModel.class);

        final OpinionsAdapter adapter = new OpinionsAdapter(this);

        viewModel.getPostList(motion_id).observe(this, adapter::submitList);

        viewModel.networkState.observe(this, networkState -> {
            adapter.setNetworkState(networkState);
            hideProgressBar();
        });
        recyclerView.addItemDecoration(new DividerItemDecoration((this),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onRetryClick(View view, int position) {

    }

}
