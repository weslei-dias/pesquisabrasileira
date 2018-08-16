package com.domain.androidcrud.request;


import com.domain.androidcrud.model.Cliente;
import com.domain.androidcrud.model.User;

import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

public interface APIService {

    @POST("salvar")
    Observable<Cliente> savePost(@Header("Authorization") String authorization,
                                 @Body Cliente cliente);

    @POST("login/")
    @FormUrlEncoded
    Observable<User> login(@Field("username") String username,
            @Field("password") String password);
}
