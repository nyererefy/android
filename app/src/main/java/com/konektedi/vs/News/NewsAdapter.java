package com.konektedi.vs.News;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.R;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Context mContext;
    private List<NewsModel> newsList;

    NewsAdapter(Context mContext, List<NewsModel> newsList) {
        this.mContext = mContext;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsAdapter.ViewHolder holder, final int position) {
        holder.titleView.setText(newsList.get(position).getTitle());
        holder.timeView.setText(newsList.get(position).getTime());
        holder.nameView.setText(newsList.get(position).getName());

        holder.titleView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, NewsView.class);
            intent.putExtra("post_id", newsList.get(position).getPost_id());
            intent.putExtra("post", newsList.get(position).getPost());
            intent.putExtra("title", newsList.get(position).getTitle());
            mContext.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        if (newsList != null)
            return newsList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView timeView, titleView, nameView;

        ViewHolder(View itemView) {
            super(itemView);

            timeView = itemView.findViewById(R.id.timeView);
            titleView = itemView.findViewById(R.id.titleView);
            nameView = itemView.findViewById(R.id.nameView);
        }
    }
}