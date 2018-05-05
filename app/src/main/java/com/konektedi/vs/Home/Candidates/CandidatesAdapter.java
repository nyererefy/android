package com.konektedi.vs.Home.Candidates;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
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
import com.konektedi.vs.Student.StudentPreferences;
import com.konektedi.vs.Utilities.ApiUtilities;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.konektedi.vs.Utilities.Constants.CLASS_NAME;
import static com.konektedi.vs.Utilities.Constants.COVER;
import static com.konektedi.vs.Utilities.Constants.DESCRIPTION;
import static com.konektedi.vs.Utilities.Constants.ID;
import static com.konektedi.vs.Utilities.Constants.NAME;
import static com.konektedi.vs.Utilities.Constants.UNIVERSITY;
import static com.konektedi.vs.Utilities.Constants.USERNAME;

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

        if (candidatesList.get(position).getVoting_state().equals("disabled")) {
            holder.voteBtn.setVisibility(View.GONE);
            ((Candidates) mContext).showAlert(R.string.voting_disabled);
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

        intent.putExtra(COVER, candidatesList.get(position).getCover());
        intent.putExtra(NAME, candidatesList.get(position).getName());
        intent.putExtra(CLASS_NAME, candidatesList.get(position).getClassName());
        intent.putExtra(DESCRIPTION, candidatesList.get(position).getDiscription());

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

    private void submitVote(final int position) {

        ((Candidates) mContext).showProgressBar();

        Map<String, String> map = new HashMap<>();

        map.put("id", StudentPreferences.getPreference(mContext, ID));
        map.put("university_id", StudentPreferences.getPreference(mContext, UNIVERSITY));
        map.put("election_id", candidatesList.get(position).getElection_id());
        map.put("category_id", candidatesList.get(position).getCategory_id());
        map.put("candidate_id", candidatesList.get(position).getCandidate_id());
        map.put("ip", getLocalIpAddress());
        map.put("from", "VS Android App");
        map.put("device", getDeviceName());

        Call<ResponseBody> call = ApiUtilities.getClient().vote(map);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ((Candidates) mContext).hideProgressBar();

                if (response.code() == 201) {
                    voteFor(position);
                } else if (response.code() == 405) {
                    cantVoteTwice();
                } else {
                    onFailureVote();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ((Candidates) mContext).hideProgressBar();
                Toast.makeText(mContext, "Error in connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private String getDeviceName() {
        return Build.MANUFACTURER
                + " " + Build.MODEL + " " + Build.VERSION.RELEASE
                + " " + Build.VERSION_CODES.class.getFields()[Build.VERSION.SDK_INT].getName();
    }

    private void onFailureVote() {
        ((Candidates) mContext).showAlert(R.string.on_failure_vote);
    }

    private void cantVoteTwice() {
        ((Candidates) mContext).showAlert(R.string.cant_vote_twice);
    }

    private void voteFor(final int position) {

        String candidate_name = candidatesList.get(position).getName();
        String username = StudentPreferences.getPreference(mContext, USERNAME);

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Thanks " + username + "!");
        builder.setMessage("You have successfully voted for " + candidate_name + ".");
        builder.setCancelable(false);

        builder.setNeutralButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ((Candidates) mContext).finishVote();
            }
        });

        builder.show();
    }


}
