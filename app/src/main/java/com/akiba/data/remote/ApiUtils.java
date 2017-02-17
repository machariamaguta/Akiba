package com.akiba.data.remote;

/**
 * Created by Leyo on 17/02/2017.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://app.akiba.devs.mobi/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}