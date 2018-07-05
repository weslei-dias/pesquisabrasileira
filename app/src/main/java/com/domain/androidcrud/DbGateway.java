package com.domain.androidcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import lombok.Getter;

public class DbGateway {

    private static DbGateway gateway;

    @Getter
    private SQLiteDatabase db;

    public DbGateway(Context context) {
        DBHelper helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public static  DbGateway getInstance(Context context){
        if (gateway == null){
            gateway = new DbGateway(context);
        }
        return gateway;
    }
}
