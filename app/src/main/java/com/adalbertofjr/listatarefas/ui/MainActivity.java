package com.adalbertofjr.listatarefas.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.adapter.TarefaListAdapter;
import com.adalbertofjr.listatarefas.dao.TarefasDAO;
import com.adalbertofjr.listatarefas.dominio.Tarefas;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * Created by AdalbertoF on 13/08/2015.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private ListView mTarefasList;
    private TarefaListAdapter mAdapter;
    private List<Tarefas> mTarefas;
    private FloatingActionButton mFabTarefa;
    private Toolbar mToolbarBottom;
    private ImageButton mBotaoPronto;
    private ImageButton mBotaoEditar;
    private Tarefas mTarefaSelecionada = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarToolbar();
        iniciarToolbarBottom();

        mTarefasList = (ListView) findViewById(R.id.lstTarefas);
        this.mTarefasList.setSelector(R.drawable.item_tarefa_selected);


        mFabTarefa = (FloatingActionButton) findViewById(R.id.fabTarefa);
        mFabTarefa.setOnClickListener(this);
        mFabTarefa.attachToListView(mTarefasList);

        // Eventos
        mTarefasList.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarListaTarefas();
        showToolbarBottom(false);
    }

    private void carregarListaTarefas() {
        mTarefas = buscarTarefas();
        mAdapter = new TarefaListAdapter(this, mTarefas);
        mTarefasList.setAdapter(mAdapter);
        mTarefasList.setEmptyView(findViewById(R.id.empty_list));
    }

    private void iniciarToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.titulo_activity_main);
    }

    private void iniciarToolbarBottom() {
        mToolbarBottom = (Toolbar) findViewById(R.id.toolbar_bottom);
        mBotaoPronto = (ImageButton) findViewById(R.id.btnPronto);
        mBotaoEditar = (ImageButton) findViewById(R.id.btnEditar);
        mBotaoPronto.setOnClickListener(this);
        mBotaoEditar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mTarefaSelecionada != null && mTarefaSelecionada.getId() == mTarefas.get(position).getId()) {
            mTarefaSelecionada = null;
            view.setSelected(false);
            showToolbarBottom(false);
        } else {
            mTarefaSelecionada = mTarefas.get(position);
            showToolbarBottom(true);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabTarefa) {
            startActivity(new Intent(this, TarefaActivity.class));
        }

        if (v.getId() == R.id.btnPronto) {
            encerrarTarefa();
        }

        if (v.getId() == R.id.btnEditar) {
            editarTarefa();
        }

    }

    private void editarTarefa() {
        Intent intent = new Intent(this, TarefaActivity.class);
        intent.putExtra(TarefaActivity.EXTRA_TAREFA, mTarefaSelecionada);
        startActivity(intent);
    }

    private void encerrarTarefa() {
        mTarefaSelecionada.setEncerrado(1);
        new TarefasDAO(this).salvar(mTarefaSelecionada);
        mAdapter.remove(mTarefaSelecionada);
        mAdapter.notifyDataSetChanged();
        showToolbarBottom(false);
    }

    private boolean isTablet() {
        return findViewById(R.id.container_tarefa) != null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showToolbarBottom(boolean show) {
        if (show) {
            mFabTarefa.hide();
            mToolbarBottom.setVisibility(View.VISIBLE);
        } else {
            mToolbarBottom.setVisibility(View.INVISIBLE);
            mFabTarefa.show();
        }
    }

    private List<Tarefas> buscarTarefas() {
        String filtro = "encerrado == 0";
        return new TarefasDAO(this).buscarTarefas(filtro);
    }
}
