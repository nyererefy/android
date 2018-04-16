package com.konektedi.vs.Motions;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.konektedi.vs.Motions.Opinions.Opinions;
import com.konektedi.vs.R;

import java.util.List;

/**
 * Created by Sy on b/a/2018.
 */

public class MotionsAdapter extends RecyclerView.Adapter<MotionsAdapter.ViewHolder> {

    private Context mContext;
    private List<MotionsModel> motionsList;

    MotionsAdapter(Context mContext, List<MotionsModel> motionsList) {
        this.mContext = mContext;
        this.motionsList = motionsList;
    }

    @NonNull
    @Override
    public MotionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_motion_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MotionsAdapter.ViewHolder holder, final int position) {
        holder.title.setText(motionsList.get(position).getTitle());

        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MotionsView.class);
                intent.putExtra("motion_id", motionsList.get(position).getMotion_id());
                intent.putExtra("motion", motionsList.get(position).getMotion());
                intent.putExtra("title", motionsList.get(position).getTitle());
                mContext.startActivity(intent);
            }
        });

        if (motionsList.get(position).getParticipated().equals("yes")) {
            holder.participateBtn.setVisibility(View.GONE);

            holder.yesCount.setText(motionsList.get(position).getYes_counts());
            holder.noCount.setText(motionsList.get(position).getNo_counts());
            holder.opinionsCount.setText(motionsList.get(position).getOpinions_counts());

            holder.opinionsCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, Opinions.class);
                    intent.putExtra("motion_id", motionsList.get(position).getMotion_id());
                    intent.putExtra("title", motionsList.get(position).getTitle());
                    mContext.startActivity(intent);
                }
            });

        } else {

            holder.opinionsCount.setVisibility(View.GONE);
            holder.noCount.setVisibility(View.GONE);
            holder.yesCount.setVisibility(View.GONE);

            holder.participateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, Participate.class);
                    intent.putExtra("motion_id", motionsList.get(position).getMotion_id());
                    intent.putExtra("motion", motionsList.get(position).getMotion());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (motionsList != null)
            return motionsList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        Button yesCount, opinionsCount, noCount, participateBtn;

        ViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            yesCount = itemView.findViewById(R.id.yesCount);
            opinionsCount = itemView.findViewById(R.id.opinionsCount);
            noCount = itemView.findViewById(R.id.noCount);
            participateBtn = itemView.findViewById(R.id.participateBtn);

        }
    }
}

