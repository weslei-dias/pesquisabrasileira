package com.domain.androidcrud.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "popbe";
    private static final int DATABASE_VERSION = 4;
    private final String CREATE_TABLE_PESQUISA = "CREATE TABLE " +
            "pesquisa (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    private final String CREATE_TABLE_USER = "CREATE TABLE " +
            "user (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL," +
            "token TEXT NOT NULL, " +
            "nome TEXT NOT NULL," +
            "unidade TEXT NOT NULL );";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("drop table if exists pesquisa");
        sqLiteDatabase.execSQL("drop table if exists user");
        sqLiteDatabase.execSQL(CREATE_TABLE_PESQUISA);
        sqLiteDatabase.execSQL(CREATE_TABLE_USER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists popbe");
        sqLiteDatabase.execSQL("drop table if exists user");
        onCreate(sqLiteDatabase);
    }
}
