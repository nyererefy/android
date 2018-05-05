package com.konektedi.vs.News.Comments;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.R;

import java.util.List;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.ViewHolder> {

    private Context mContext;
    private List<CommentsModel> commentsList;

    public CommentsAdapter(Context mContext, List<CommentsModel> commentsList) {
        this.mContext = mContext;
        this.commentsList = commentsList;
    }

    @NonNull
    @Override
    public CommentsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_comment, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentsAdapter.ViewHolder holder, final int position) {
        holder.commentView.setText(commentsList.get(position).getComment());
        holder.timeView.setText(commentsList.get(position).getTime());
        holder.nameView.setText(commentsList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (commentsList != null)
            return commentsList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView timeView, commentView, nameView;

        ViewHolder(View itemView) {
            super(itemView);

            timeView = itemView.findViewById(R.id.timeView);
            commentView = itemView.findViewById(R.id.commentView);
            nameView = itemView.findViewById(R.id.nameView);
        }
    }
}
