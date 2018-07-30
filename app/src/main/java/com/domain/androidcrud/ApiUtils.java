package com.domain.androidcrud;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://18.216.169.97:9441/popbe/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL)
                .create(APIService.class);
    }
}
