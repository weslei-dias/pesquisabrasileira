package com.domain.androidcrud.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.domain.androidcrud.db.DbGateway;
import com.domain.androidcrud.model.User;

public class UserDao {

    private final String USER = "user";
    private DbGateway gw;

    public UserDao(Context ctx){
        gw = DbGateway.getInstance(ctx);
    }

    public boolean salvar(Integer id, ContentValues cv){
        if (id != null && id > 0) {
            return gw.getDb().update(USER, cv, "id=?",
                    new String[]{id + ""}) > 0;
        } else {
            return gw.getDb().insert(USER, null, cv) > 0;
        }
    }

    public User getUsuarioComUsername(String username){
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM user where username='" + username +
                "'",null);
        User user = new User();

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex("id"));
            String usuario = cursor.getString(cursor.getColumnIndex("username"));
            String token = cursor.getString(cursor.getColumnIndex("token"));
            String nome = cursor.getString(cursor.getColumnIndex("nome"));
            String unidade = cursor.getString(cursor.getColumnIndex("unidade"));
            user = new User(usuario, token);
            user.setId(id);
            user.setNome(nome);
            user.setUnidade(unidade);
        }
        cursor.close();

        return user;
    }
}
