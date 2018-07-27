package com.domain.androidcrud;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://80b2192c.ngrok.io/popbe/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);
    }
}
