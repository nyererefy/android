package com.konektedi.vs.Petitions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.Elections.CategoriesModel;
import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on 4/1/2018.
 */

public class PetitionsAdapter extends RecyclerView.Adapter {

    private List<PetitionsModel> petitionsModelList;
    private Context context;
    private List<CategoriesModel> categoriesModelList;

    PetitionsAdapter(Context c, List<PetitionsModel> petitionsModelList) {
        this.petitionsModelList = petitionsModelList;
        this.context = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.z_petiton_item, parent, false);

        return new PetitionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        PetitionsModel petition = petitionsModelList.get(position);
        ((PetitionViewHolder) holder).bind(petition);
    }

    @Override
    public int getItemCount() {
        return petitionsModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class PetitionViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        PetitionViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, PetitionView.class);
                    context.startActivity(intent);
                }
            });
        }

        void bind(PetitionsModel petition) {
            title.setText(petition.getTitle());
        }


    }


}

