package com.konektedi.vs.home.elections;

/**
 * Created by Sy on 3/28/2018.
 */

public class ElectionsModel {

    private String election_id, election_title, opened;

    public String getElection_id() {
        return election_id;
    }

    public void setElection_id(String election_id) {
        this.election_id = election_id;
    }

    public String getElection_title() {
        return election_title;
    }

    public void setElection_title(String election_title) {
        this.election_title = election_title;
    }

    public String getOpened() {
        return opened;
    }

    public void setOpened(String opened) {
        this.opened = opened;
    }
}
