package com.adalbertofjr.listatarefas.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.adalbertofjr.listatarefas.data.TarefasDbHelper;
import com.adalbertofjr.listatarefas.dominio.Tarefas;

import java.util.ArrayList;
import java.util.List;

import static com.adalbertofjr.listatarefas.data.TarefasContract.TarefaEntry;

/**
 * Created by AdalbertoF on 23/08/2015.
 */
public class TarefasDAO {

    private TarefasDbHelper mDbHelper;
    private SQLiteDatabase mDb;

    public TarefasDAO(Context context) {
        mDbHelper = new TarefasDbHelper(context);
    }

    private SQLiteDatabase getDb() {
        if (mDb == null) {
            mDb = mDbHelper.getWritableDatabase();
        }
        return mDb;
    }

    private void closeDb() {
        mDb.close();
    }

    private Long inserir(Tarefas tarefa) {
        long id = getDb().insert(TarefaEntry.TABLE_NAME, null, tarefaToValue(tarefa));

        closeDb();
        return id;
    }

    private long atualizar(Tarefas tarefa) {
        String where = TarefaEntry.ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(tarefa.getId())};
        long linhasAlteradas = getDb().update(TarefaEntry.TABLE_NAME, tarefaToValue(tarefa), where, whereArgs);

        closeDb();
        return linhasAlteradas;
    }

    public Long salvar(Tarefas tarefa) {
        if (tarefa.getId() == 0) {
            return inserir(tarefa);
        } else {
            return atualizar(tarefa);
        }
    }

    public int excluir(Tarefas tarefa) {
        String where = TarefaEntry.ID + "=?";
        String[] whereArgs = new String[]{String.valueOf(TarefaEntry.ID)};

        int linhasExcluidas = getDb().update(TarefaEntry.TABLE_NAME, tarefaToValue(tarefa), where, whereArgs);

        closeDb();
        return linhasExcluidas;
    }

    public List<Tarefas> buscarTarefas(String filtro) {
        String sql = "SELECT * FROM " + TarefaEntry.TABLE_NAME;
        String[] argumentos = null;

        if (filtro != null) {
            sql += " WHERE " + filtro;
            //argumentos = new String[]{filtro};
        }

        sql += " ORDER BY " + TarefaEntry.DATA_VENCIMENTO;

        Cursor cursor = getDb().rawQuery(sql, null);

        List<Tarefas> tarefas = new ArrayList<>();

        while (cursor.moveToNext()) {
            Tarefas tarefa = new Tarefas();
            tarefa.setId(cursor.getLong(cursor.getColumnIndex(TarefaEntry.ID)));
            tarefa.setTitulo(cursor.getString(cursor.getColumnIndex(TarefaEntry.TITULO)));
            tarefa.setDataVencimento(cursor.getString(cursor.getColumnIndex(TarefaEntry.DATA_VENCIMENTO)));
            tarefa.setPrioridade(cursor.getInt(cursor.getColumnIndex(TarefaEntry.PRIORIDADE)));
            tarefa.setObservacao(cursor.getString(cursor.getColumnIndex(TarefaEntry.OBSERVACAO)));
            tarefa.setEncerrado(cursor.getInt(cursor.getColumnIndex(TarefaEntry.ENCERRADO)));

            tarefas.add(tarefa);
        }

        cursor.close();
        closeDb();

        return tarefas;
    }

    private ContentValues tarefaToValue(Tarefas tarefa) {
        ContentValues values = new ContentValues();

        if(tarefa.getId()!=0) values.put(TarefaEntry.ID, tarefa.getId());
        values.put(TarefaEntry.TITULO, tarefa.getTitulo());
        values.put(TarefaEntry.DATA_VENCIMENTO, tarefa.getDataVencimento());
        values.put(TarefaEntry.PRIORIDADE, tarefa.getPrioridade());
        values.put(TarefaEntry.OBSERVACAO, tarefa.getObservacao());
        values.put(TarefaEntry.ENCERRADO, tarefa.getEncerrado());

        return values;
    }

}
