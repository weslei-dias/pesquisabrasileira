package com.domain.androidcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Crud.db";
    private static final int DATABASE_VERSION = 1;
    private final String CREATE_TABLE = "CREATE TABLE " +
            "popbe (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "data_pesquisa TEXT NOT NULL," +
            "nome_entrevistador TEXT NOT NULL, " +
            "unidade_entrevista TEXT NOT NULL, " +
            "nome_entrevistado TEXT NOT NULL, " +
            "cidade TEXT NOT NULL, " +
            "cep INTEGER NOT NULL, " +
            "numero INTEGER NOT NULL, " +
            "bairro TEXT NOT NULL, " +
            "estado TEXT NOT NULL, " +
            "rua TEXT NOT NULL, " +
            "complemento TEXT, " +
            "telefone INTEGER NOT NULL, " +
            "email TEXT NOT NULL, " +
            "idade TEXT NOT NULL, " +
            "local_pesquisa TEXT NOT NULL, " +
            "ocupacao TEXT NOT NULL, " +
            "escolaridade TEXT NOT NULL, " +
            "area_graduacao TEXT NOT NULL, " +
            "opcao_pos TEXT NOT NULL, " +
            "pretencao_inicio_pos TEXT NOT NULL, " +
            "paticipar_sorteio TEXT NOT NULL, " +
            "sexo TEXT," +
            "inicio_pos TEXT," +
            "outro_local TEXT," +
            "outra_area TEXT);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
