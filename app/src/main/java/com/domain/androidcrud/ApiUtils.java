package com.domain.androidcrud;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://55d5e5f2.ngrok.io/popbe/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL)
                .create(APIService.class);
    }
}
