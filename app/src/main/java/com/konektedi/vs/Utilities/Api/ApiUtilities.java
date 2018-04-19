package com.konektedi.vs.Utilities.Api;

/**
 * Created by Sy on b/9/2018.
 */

public class ApiUtilities {

    private ApiUtilities() {
    }

    private static final String BASE_URL = "http://192.168.43.188/konektedi_vs/api/v1/";

    public static Api getElections() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

    public static Api getCategories() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

    public static Api getCandidates() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

    public static Api getMotions() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

    public static Api getOpinions() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

    public static Api vote() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

}
