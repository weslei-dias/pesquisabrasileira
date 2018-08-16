package com.domain.androidcrud.request;

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://18.216.169.97:9441/popbe/";
    public static final String URL_VETROL = "https://vetrol.2x3.com.br/api/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL)
                .create(APIService.class);
    }
    public static APIService getAPIServiceVetrol() {

        return RetrofitClient.getClient(URL_VETROL)
                .create(APIService.class);
    }
}
