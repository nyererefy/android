package com.konektedi.vs.motions.opinions;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class Opinions {

    private String  opinion, vote, time, name;
    int opinion_id;

    public int getOpinion_id() {
        return opinion_id;
    }

    public void setOpinion_id(int opinion_id) {
        this.opinion_id = opinion_id;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static final DiffUtil.ItemCallback<Opinions> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Opinions>() {
                @Override
                public boolean areItemsTheSame(
                        @NonNull Opinions opinion, @NonNull Opinions newFeed) {
                    // Opinions properties may have changed if reloaded from the DB, but ID is fixed
                    return opinion.getOpinion_id() == newFeed.getOpinion_id();
                }

                @Override
                public boolean areContentsTheSame(
                        @NonNull Opinions opinion, @NonNull Opinions newFeed) {
                    // NOTE: if you use equals, your object must properly override Object#equals()
                    // Incorrectly returning false here will result in too many animations.
                    return opinion.equals(newFeed);
                }
            };
}
