package com.konektedi.vs.Elections;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on 4/1/2018.
 */

public class ElectionsAdapter extends RecyclerView.Adapter {

    private List<ElectionsModel> electionsModelList;
    private Context context;
    private List<CategoriesModel> categoriesModelList;

    ElectionsAdapter(Context c, List<ElectionsModel> electionsModelList) {
        this.electionsModelList = electionsModelList;
        this.context = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.z_election_item, parent, false);

        return new ElectionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ElectionsModel election = electionsModelList.get(position);
        ((ElectionViewHolder) holder).bind(election);
    }

    @Override
    public int getItemCount() {
        return electionsModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class ElectionViewHolder extends RecyclerView.ViewHolder {

        TextView electionTitle;

        ElectionViewHolder(View itemView) {
            super(itemView);

            electionTitle = itemView.findViewById(R.id.electionTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ElectionView.class);
                    context.startActivity(intent);
                }
            });
        }

        void bind(ElectionsModel election) {
            electionTitle.setText(election.getElection());
        }


    }


}
