package com.konektedi.vs.Motions;

/**
 * Created by Sy on 3/28/2018.
 */

public class MotionsModel {
    private int motion_id;
    private String title,time,name;

    public int getMotion_id() {
        return motion_id;
    }

    public void setMotion_id(int motion_id) {
        this.motion_id = motion_id;
    }

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
}
