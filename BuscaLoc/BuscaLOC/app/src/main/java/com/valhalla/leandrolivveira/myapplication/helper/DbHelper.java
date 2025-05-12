package com.valhalla.leandrolivveira.myapplication.helper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {
    public static int VERSION = 1;
    public static String NOME_DB = "DB_POSTAGENS";
    public static String TABELA_POSTAGENS = "postagens";

    public DbHelper(@Nullable Context context) {
        super(context, NOME_DB, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABELA_POSTAGENS
                + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "codigo TEXT NOT NULL, " +
                "produto TEXT NOT NULL, " +
                "quantidade TEXT NOT NULL, " +
                "localidade TEXT NOT NULL ); ";

        try{
            db.execSQL(sql);
            Log.i("INFO DB", "Sucesso ao criar a tabela" );
        }catch (Exception e){
            Log.i("INFO DB", "Erro ao criar a tabela" + e.getMessage());
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
