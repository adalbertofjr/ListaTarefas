package com.adalbertofjr.listatarefas.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.adalbertofjr.listatarefas.data.TarefasContract.TarefaEntry;

/**
 * Created by AdalbertoF on 23/08/2015.
 */
public class TarefasDbHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "tarefas.db";
    private static final int DATABASE_VERSION = 1;

    public TarefasDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TABLE_TAREFAS = "CREATE TABLE " + TarefaEntry.TABLE_NAME + "("
                + TarefaEntry.ID + " INTEGER PRIMARY KEY, "
                + TarefaEntry.TITULO + " TEXT, "
                + TarefaEntry.DATA_VENCIMENTO + " TEXT, "
                + TarefaEntry.PRIORIDADE + " INTEGER, "
                + TarefaEntry.OBSERVACAO + " TEXT, "
                + TarefaEntry.ENCERRADO + " INTEGER"
                + ");";

        db.execSQL(SQL_CREATE_TABLE_TAREFAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TarefaEntry.TABLE_NAME);
        onCreate(db);
    }
}
