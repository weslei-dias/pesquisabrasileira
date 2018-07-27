package com.domain.androidcrud;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Crud.db";
    private static final int DATABASE_VERSION = 4;
    private final String CREATE_TABLE = "CREATE TABLE " +
            "popbe (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "data_pesquisa TEXT NOT NULL," +
            "nome_entrevistador TEXT NOT NULL, " +
            "unidade_entrevista TEXT, " +
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
            "local_pesquisa TEXT, " +
            "ocupacao TEXT, " +
            "escolaridade TEXT, " +
            "deseja_graduacao TEXT, " +
            "area_graduacao TEXT, " +
            "opcao_pos TEXT, " +
            "qual_pos TEXT, " +
            "pretencao_inicio_pos TEXT, " +
            "paticipar_sorteio TEXT, " +
            "sexo TEXT," +
            "inicio_pos TEXT," +
            "outro_local TEXT," +
            "tempo_conclusao_graduacao TEXT," +
            "inicio_primeira_graduacao TEXT," +
            "inicio_segunda_graduacao TEXT," +
            "outra_area TEXT," +
            "gerou_prospect INTEGER," +
            "numero_prospect TEXT," +
            "status_prospect INTEGER" +
            ");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists popbe");
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists popbe");
        onCreate(sqLiteDatabase);
    }
}
