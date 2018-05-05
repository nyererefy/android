package com.konektedi.vs.Home.Elections;

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

public class ElectionsAdapter extends RecyclerView.Adapter<ElectionsAdapter.ViewHolder> {

    private Context mContext;
    private List<ElectionsModel> electionList;

    ElectionsAdapter(Context mContext, List<ElectionsModel> electionList) {
        this.mContext = mContext;
        this.electionList = electionList;
    }

    @NonNull
    @Override
    public ElectionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_election_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ElectionsAdapter.ViewHolder holder, final int position) {
        holder.title.setText(electionList.get(position).getElection_title().toUpperCase());


        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ElectionView.class);
                intent.putExtra("election_id", electionList.get(position).getElection_id());
                intent.putExtra("election_title", electionList.get(position).getElection_title());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (electionList != null)
            return electionList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

        }
    }
}
