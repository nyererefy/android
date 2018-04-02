package com.konektedi.vs.Elections;

/**
 * Created by Sy on 4/1/2018.
 */

public class BtnModel {
    private int position;
    private String title;

    public BtnModel(int position, String title) {
        this.position = position;
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
