package com.konektedi.vs.Utilities;

public class NetworkState {
    private final NetworkStatus status;
    private final String msg;

    public static final NetworkState LOADED;
    public static final NetworkState LOADING;

    public NetworkState(NetworkStatus status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    static {
        LOADED=new NetworkState(NetworkStatus.SUCCESS,"Success");
        LOADING=new NetworkState(NetworkStatus.RUNNING,"Running");
    }

    public NetworkStatus getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }
}