package com.konektedi.vs.Petitions;

/**
 * Created by Sy on 3/28/2018.
 */

public class PetitionsModel {
    private int petition_id;
    private String title,time,name;

    public int getPetition_id() {
        return petition_id;
    }

    public void setPetition_id(int petition_id) {
        this.petition_id = petition_id;
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
