package com.domain.androidcrud.request;


import com.domain.androidcrud.model.Cliente;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("salvar")
    Observable<Cliente> savePost(@Body Cliente cliente);
}
