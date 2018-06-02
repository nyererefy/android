package com.konektedi.vs.news.Comments;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class Comments {

    private String comment_id,comment,time,name;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final DiffUtil.ItemCallback<Comments> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Comments>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Comments comment, @NonNull Comments newFeed) {
                    // Comments properties may have changed if reloaded from the DB, but ID is fixed
                    return comment.getComment_id() == newFeed.getComment_id();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Comments comment, @NonNull Comments newFeed) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return comment.equals(newFeed);
                }
            };
}
