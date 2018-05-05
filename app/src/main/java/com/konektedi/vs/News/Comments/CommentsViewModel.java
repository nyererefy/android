package com.konektedi.vs.News.Comments;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {

    private LiveData<List<CommentsModel>> comments;
    private CommentsRepository commentsRepository = new CommentsRepository();

    public CommentsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<CommentsModel>> getAllComments(String comment_id) {
        comments = commentsRepository.getComments(comment_id);
        return comments;
    }

}
