package com.konektedi.vs.Home.Candidates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.Api.ApiUtilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Sy on b/14/2018.
 */

public class CandidatesAdapter extends RecyclerView.Adapter<CandidatesAdapter.ViewHolder> {

    private Context mContext;
    private List<CandidatesModel> candidatesList;

    CandidatesAdapter(Context mContext, List<CandidatesModel> candidatesList) {
        this.mContext = mContext;
        this.candidatesList = candidatesList;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView nameView, schoolView;
        Button voteBtn;
        ImageView cover;

        ViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.nameView);
            schoolView = itemView.findViewById(R.id.schoolView);
            voteBtn = itemView.findViewById(R.id.voteBtn);
            cover = itemView.findViewById(R.id.cover);

        }
    }

    @NonNull
    @Override
    public CandidatesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(mContext).inflate(R.layout.z_candidate, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidatesAdapter.ViewHolder holder,
                                 @SuppressLint("RecyclerView") final int position) {

        holder.nameView.setText(candidatesList.get(position).getName());
        holder.schoolView.setText(candidatesList.get(position).getClassName());

        if (candidatesList.get(position).getParticipated().equals("yes")) {
            holder.voteBtn.setVisibility(View.GONE);
        } else {
            holder.voteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirm(position);
                }
            });
        }

        holder.nameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfile(position);
            }
        });

        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProfile(position);
            }
        });

        String coverURL = candidatesList.get(position).getCover();

        Glide.with(mContext)
                .load(coverURL)
                .apply(new RequestOptions()
                        .dontAnimate()
                        .placeholder(R.drawable.holder)
                        .error(R.drawable.holder))
                .into(holder.cover);
    }

    @Override
    public int getItemCount() {
        if (candidatesList != null)
            return candidatesList.size();
        else return 0;
    }

    private void showProfile(int position) {
        Intent intent = new Intent(mContext, Profile.class);

        intent.putExtra("cover", candidatesList.get(position).getCover());
        intent.putExtra("name", candidatesList.get(position).getName());
        intent.putExtra("school", candidatesList.get(position).getClassName());
        intent.putExtra("discription", candidatesList.get(position).getDiscription());

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

    private void confirm(final int position) {

        String name = candidatesList.get(position).getName();

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Confirm action!");
        builder.setMessage("You are about to vote for " + name + ". Do you really want to proceed?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes! Vote now", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                submitVote(position);
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
    }

    private void submitVote(int position) {

        Map<String, String> map = new HashMap<>();

        map.put("id", "1");
        map.put("username", "app");
        map.put("university_id", "1"); //BADO IP HAPA
        map.put("election_id", candidatesList.get(position).getElection_id());
        map.put("category_id", candidatesList.get(position).getCategory_id());
        map.put("candidate_id", candidatesList.get(position).getCandidate_id());

        Log.i("mapddd", map.toString());

        Call<ResponseBody> call = ApiUtilities.vote().vote(map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(mContext, response.toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mContext, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
