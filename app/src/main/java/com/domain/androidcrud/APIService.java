package com.domain.androidcrud;


import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("salvar")
    Observable<Cliente> savePost(@Body Cliente cliente);
}
