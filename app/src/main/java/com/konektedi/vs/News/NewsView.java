package com.konektedi.vs.News;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.konektedi.vs.News.Comments.CommentsAdapter;
import com.konektedi.vs.News.Comments.CommentsModel;
import com.konektedi.vs.News.Comments.CommentsViewModel;
import com.konektedi.vs.R;
import com.konektedi.vs.Student.StudentPreferences;
import com.konektedi.vs.Utilities.ApiUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.Utilities.Constants.ID;
import static com.konektedi.vs.Utilities.Constants.UNIVERSITY;

public class NewsView extends AppCompatActivity {

    TextView titleView, postView;
    RecyclerView recyclerView;
    CommentsViewModel viewModel;
    CommentsAdapter adapter;
    ProgressBar progressBar;
    EditText commentInput;
    ImageButton commentSubmitBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        titleView = findViewById(R.id.title);
        postView = findViewById(R.id.post);
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
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
        final String post_id = data.getString("post_id");


        setTitle(R.string.news);
        titleView.setText(title);
        postView.setText(post);

        getComments(post_id);

        commentSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addComment(post_id);
            }
        });

    }

    private void getComments(String post_id) {
        showProgressBar();

        viewModel = ViewModelProviders.of(this).get(CommentsViewModel.class);
        viewModel.getAllComments(post_id).observe(this, new Observer<List<CommentsModel>>() {
            @Override
            public void onChanged(@Nullable List<CommentsModel> commentsModels) {
                adapter = new CommentsAdapter(NewsView.this, commentsModels);
                recyclerView.setLayoutManager(new LinearLayoutManager(NewsView.this));
                recyclerView.setAdapter(adapter);
                hideProgressBar();
            }
        });
    }

    private void addComment(final String post_id) {

        final String comment = commentInput.getText().toString();

        Map<String, String> map = new HashMap<>();

        map.put("id", StudentPreferences.getPreference(this, ID));
        map.put("comment", comment);
        map.put("post_id", post_id);
        map.put("university_id", StudentPreferences.getPreference(this, UNIVERSITY));

        hideKeyboard();
        commentInput.setText("");
        showProgressBar();

        Call<ResponseBody> call = ApiUtilities.getClient().postComment(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
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
                Toast.makeText(NewsView.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
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

}
