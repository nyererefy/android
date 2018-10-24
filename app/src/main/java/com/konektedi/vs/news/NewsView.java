package com.konektedi.vs.news;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.konektedi.vs.news.comments.CommentsAdapter;
import com.konektedi.vs.news.comments.CommentsViewModel;
import com.konektedi.vs.R;
import com.konektedi.vs.utilities.api.ApiUtilities;
import com.konektedi.vs.utilities.ListItemClickListener;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.student.UserPreferencesKt.grabPreference;
import static com.konektedi.vs.utilities.Constants.ID;
import static com.konektedi.vs.utilities.Constants.UNIVERSITY;

public class NewsView extends AppCompatActivity implements ListItemClickListener {
    private String TAG = "NewsView";

    TextView titleView, postView;
    RecyclerView recyclerView;
    CommentsViewModel viewModel;
    EditText commentInput;
    ImageButton commentSubmitBtn;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        titleView = findViewById(R.id.title);
        postView = findViewById(R.id.post);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        commentSubmitBtn = findViewById(R.id.commentSubmitBtn);
        commentInput = findViewById(R.id.commentInput);
        commentInput.addTextChangedListener(textWatcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getPost();
    }

    private void getPost() {
        Bundle data = getIntent().getExtras();

        assert data != null;
        String post = data.getString("post");
        String title = data.getString("title");
        final int post_id = data.getInt("post_id");


        setTitle(R.string.news);
        titleView.setText(title);
        postView.setText(post);

        getComments(post_id);

        commentSubmitBtn.setOnClickListener(view -> addComment(post_id));

    }

    private void getComments(int post_id) {
        showProgressBar();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);

        final CommentsAdapter adapter = new CommentsAdapter(this);

        viewModel.getPostList(post_id).observe(this, adapter::submitList);

        viewModel.networkState.observe(this, networkState -> {
            adapter.setNetworkState(networkState);
            hideProgressBar();
        });
        recyclerView.addItemDecoration(new DividerItemDecoration((this),
                DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void addComment(final int post_id) {
        showProgressBar();
        final String comment = commentInput.getText().toString();

        Map<String, String> map = new HashMap<>();

        map.put("id", grabPreference(this, ID));
        map.put("comment", comment);
        map.put("post_id", String.valueOf(post_id));
        map.put("university_id", grabPreference(this, UNIVERSITY));

        hideKeyboard();
        commentInput.setText("");

        Call<ResponseBody> call = ApiUtilities.getClient().postComment(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                hideProgressBar();

                if (response.isSuccessful()) {
                    hideKeyboard();
                    Toast.makeText(NewsView.this, "success", Toast.LENGTH_SHORT).show();
                    getComments(post_id);

                } else {
                    hideKeyboard();
                    commentInput.setText(comment);
                    Toast.makeText(NewsView.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                hideProgressBar();
                Toast.makeText(NewsView.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            final String comment = commentInput.getText().toString();
            if (!comment.isEmpty()) {
                commentSubmitBtn.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            final String comment = commentInput.getText().toString();
            if (comment.isEmpty()) {
                commentSubmitBtn.setVisibility(View.GONE);
            }
        }
    };

    @Override
    public void onRetryClick(View view, int position) {

    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return true;
    }

}
