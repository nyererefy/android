package com.konektedi.vs.motions;

/**
 * Created by Sy on 3/28/2018.
 */

public class MotionsModel {
    private String title, time, name, participated, motion,no_counts,yes_counts,opinions_counts;
    int motion_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getParticipated() {
        return participated;
    }

    public void setParticipated(String participated) {
        this.participated = participated;
    }

    public int getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(int motion_id) {
        this.motion_id = motion_id;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getNo_counts() {
        return no_counts;
    }

    public void setNo_counts(String no_counts) {
        this.no_counts = no_counts;
    }

    public String getYes_counts() {
        return yes_counts;
    }

    public void setYes_counts(String yes_counts) {
        this.yes_counts = yes_counts;
    }

    public String getOpinions_counts() {
        return opinions_counts;
    }

    public void setOpinions_counts(String opinions_counts) {
        this.opinions_counts = opinions_counts;
    }
}
