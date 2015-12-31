package com.adalbertofjr.listatarefas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.Spinner;

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.dominio.Tarefas;
import com.adalbertofjr.listatarefas.ui.fragments.TarefaFragment;

/**
 * Created by AdalbertoF on 13/08/2015.
 */
public class TarefaActivity extends AppCompatActivity {

    public static final String EXTRA_TAREFA = "tarefa";
    private Toolbar mToolbar;
    private Button mDataVencimento;
    private String[] mPrioridadeArray;
    private Spinner mPrioridade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa);

        iniciarToolbar();

        Intent intent = getIntent();
        Tarefas tarefa = (Tarefas) intent.getSerializableExtra(EXTRA_TAREFA);

        iniciarTarefaFragment(tarefa);
    }


    private void iniciarTarefaFragment(Tarefas tarefa) {
        TarefaFragment fragment = TarefaFragment.novaTarefa(tarefa);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_tarefa, fragment, TarefaFragment.TAG_DETALHE);
        ft.commit();
    }


    private void iniciarToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.titulo_activity_tarefa);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
