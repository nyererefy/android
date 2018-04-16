package com.konektedi.vs.Motions.Opinions;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.R;

import java.util.List;

public class OpinionsAdapter extends RecyclerView.Adapter<OpinionsAdapter.ViewHolder> {

    private Context mContext;
    private List<OpinionsModel> list;

    OpinionsAdapter(Context mContext, List<OpinionsModel> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public OpinionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_opinion, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OpinionsAdapter.ViewHolder holder, final int position) {
        holder.opinionView.setText(list.get(position).getOpinion());
        holder.timeView.setText(list.get(position).getTime());
        holder.nameView.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (list != null)
            return list.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameView, timeView, opinionView;

        ViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.nameView);
            timeView = itemView.findViewById(R.id.timeView);
            opinionView = itemView.findViewById(R.id.opinionView);

        }
    }
}