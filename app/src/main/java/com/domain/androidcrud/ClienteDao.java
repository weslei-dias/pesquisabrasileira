package com.domain.androidcrud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
    private final String TABLE_CLIENTES = "Clientes";
    private DbGateway gw;

    public ClienteDao(Context ctx) {
        gw = DbGateway.getInstance(ctx);
    }

    public boolean excluir(int id){
        return gw.getDb().delete(TABLE_CLIENTES, "ID=?", new String[]{ id + "" }) > 0;
    }

    public boolean salvar(String nome, String sexo, String uf, boolean vip) {
        return salvar(0, nome, sexo, uf, vip);
    }

        public boolean salvar(int id, String nome, String sexo, String uf, boolean vip) {
        ContentValues cv = new ContentValues();
        cv.put("Nome", nome);
        cv.put("Sexo", sexo);
        cv.put("UF", uf);
        cv.put("Vip", vip ? 1 : 0);

        if (id > 0){
            return gw.getDb().update(TABLE_CLIENTES, cv, "ID=?",
                    new String[]{id + ""}) > 0;
        }else {
            return gw.getDb().insert(TABLE_CLIENTES, null, cv) > 0;
        }
    }

    public List<Cliente> getTodosClientes() {
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = gw.getDb()
                .rawQuery("Select * from Clientes", null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            String uf = cursor.getString(cursor.getColumnIndex("UF"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            clientes.add(new Cliente(id, nome, sexo, uf, vip));
        }
        cursor.close();
        return clientes;
    }

    public Cliente retornarUltimo(){
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM Clientes ORDER BY ID DESC", null);
        if(cursor.moveToFirst()){
            int id = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            String uf = cursor.getString(cursor.getColumnIndex("UF"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            cursor.close();
            return new Cliente(id, nome, sexo, uf, vip);
        }

        return null;
    }

    public Cliente getCliente(int id){
        Cursor cursor = gw.getDb().rawQuery("SELECT * FROM Clientes where id=" + id,
                null);
        Cliente cliente = null;
        while(cursor.moveToNext()){
            int idCliente = cursor.getInt(cursor.getColumnIndex("ID"));
            String nome = cursor.getString(cursor.getColumnIndex("Nome"));
            String sexo = cursor.getString(cursor.getColumnIndex("Sexo"));
            String uf = cursor.getString(cursor.getColumnIndex("UF"));
            boolean vip = cursor.getInt(cursor.getColumnIndex("Vip")) > 0;
            cliente = new Cliente(idCliente, nome, sexo, uf, vip);
        }
        cursor.close();
        return cliente;
    }
}
