package com.domain.androidcrud.model;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class User {

    public User(String username, String token){
        this.username = username;
        this.token = token;
    }

    public User(){

    }

    private Integer id;

    @SerializedName("username")
    private String username;

    @SerializedName("password")
    private String password;

    @SerializedName("token")
    private String token;

    @SerializedName("nome")
    private String nome;

    @SerializedName("unidade")
    private String unidade;
}
