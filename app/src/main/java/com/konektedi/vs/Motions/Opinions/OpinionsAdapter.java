package com.konektedi.vs.Motions.Opinions;

import android.arch.paging.PagedListAdapter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.konektedi.vs.R;
import com.konektedi.vs.Utilities.ListItemClickListener;
import com.konektedi.vs.Utilities.NetworkState;
import com.konektedi.vs.Utilities.NetworkStatus;

public class OpinionsAdapter extends PagedListAdapter<Opinions, RecyclerView.ViewHolder> {
    private static final String TAG = "OpinionsAdapter";
    private NetworkState networkState;
    private ListItemClickListener itemClickListener;

    public OpinionsAdapter(ListItemClickListener itemClickListener) {
        super(Opinions.DIFF_CALLBACK);
        this.itemClickListener = itemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view;

        if (viewType == R.layout.z_opinion) {
            view = layoutInflater.inflate(R.layout.z_opinion, parent, false);
            return new mViewHolder(view);
        } else if (viewType == R.layout.network_state_item) {
            view = layoutInflater.inflate(R.layout.network_state_item, parent, false);
            return new NetworkStateItemViewHolder(view, itemClickListener);
        } else {
            throw new IllegalArgumentException("unknown view type");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case R.layout.z_opinion:
                ((mViewHolder) holder).bindTo(getItem(position));
                break;
            case R.layout.network_state_item:
                ((NetworkStateItemViewHolder) holder).bindView(networkState);
                break;
        }
    }

    private boolean hasExtraRow() {
        if (networkState != null && networkState != NetworkState.LOADED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (hasExtraRow() && position == getItemCount() - 1) {
            return R.layout.network_state_item;
        } else {
            return R.layout.z_opinion;
        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        NetworkState previousState = this.networkState;
        boolean previousExtraRow = hasExtraRow();
        this.networkState = newNetworkState;
        boolean newExtraRow = hasExtraRow();
        if (previousExtraRow != newExtraRow) {
            if (previousExtraRow) {
                notifyItemRemoved(getItemCount());
            } else {
                notifyItemInserted(getItemCount());
            }
        } else if (newExtraRow && previousState != newNetworkState) {
            notifyItemChanged(getItemCount() - 1);
        }
    }

    static class mViewHolder extends RecyclerView.ViewHolder {
        private TextView nameView, opinionView, timeView;


        public mViewHolder(View itemView) {
            super(itemView);
            nameView = itemView.findViewById(R.id.nameView);
            opinionView = itemView.findViewById(R.id.opinionView);
            timeView = itemView.findViewById(R.id.timeView);
        }

        public void bindTo(Opinions feed) {
            nameView.setText(feed.getName());
            opinionView.setText(feed.getOpinion());
            timeView.setText(feed.getTime());
        }
    }

    static class NetworkStateItemViewHolder extends RecyclerView.ViewHolder {

        private final ProgressBar progressBar;
        private final TextView errorMsg;
        private Button button;

        public NetworkStateItemViewHolder(View itemView, ListItemClickListener listItemClickListener) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progress_bar);
            errorMsg = itemView.findViewById(R.id.error_msg);
            button = itemView.findViewById(R.id.retry_button);

            button.setOnClickListener(view -> listItemClickListener.onRetryClick(view, getAdapterPosition()));
        }


        public void bindView(NetworkState networkState) {
            if (networkState != null && networkState.getStatus() == NetworkStatus.RUNNING) {
                progressBar.setVisibility(View.VISIBLE);
            } else {
                progressBar.setVisibility(View.GONE);
            }

            if (networkState != null && networkState.getStatus() == NetworkStatus.FAILED) {
                errorMsg.setVisibility(View.VISIBLE);
                errorMsg.setText(networkState.getMsg());
            } else {
                errorMsg.setVisibility(View.GONE);
            }
        }
    }
}
