package com.konektedi.vs.Motions;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.konektedi.vs.Home.Categories.CategoriesModel;
import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on b/a/2018.
 */

public class MotionsAdapter extends RecyclerView.Adapter {

    private List<MotionsModel> motionsModelList;
    private Context context;
    private List<CategoriesModel> categoriesModelList;

    MotionsAdapter(Context c, List<MotionsModel> motionsModelList) {
        this.motionsModelList = motionsModelList;
        this.context = c;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.z_motion_item, parent, false);

        return new MotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        MotionsModel motion = motionsModelList.get(position);
        ((MotionViewHolder) holder).bind(motion);
    }

    @Override
    public int getItemCount() {
        return motionsModelList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    class MotionViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        MotionViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, MotionsView.class);
                    context.startActivity(intent);
                }
            });
        }

        void bind(MotionsModel motion) {
            title.setText(motion.getTitle());
        }


    }


}

