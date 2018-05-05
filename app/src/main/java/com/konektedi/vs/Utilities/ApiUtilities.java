package com.konektedi.vs.Utilities;

/**
 * Created by Sy on b/9/2018.
 */

public class ApiUtilities {

    public static final String BASE_URL = "http://192.168.43.188/konektedi_vs/api/v1/";

    public static Api getClient() {
        return ApiClient.getClient(BASE_URL).create(Api.class);
    }

}
