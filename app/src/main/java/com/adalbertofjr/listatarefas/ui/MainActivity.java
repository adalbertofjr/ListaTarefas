package com.adalbertofjr.listatarefas.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.adapter.TarefaListAdapter;
import com.adalbertofjr.listatarefas.dao.TarefasDAO;
import com.adalbertofjr.listatarefas.dominio.Tarefas;
import com.adalbertofjr.listatarefas.ui.fragments.TarefaFragment;
import com.adalbertofjr.listatarefas.ui.fragments.TarefaViewFragment;
import com.melnykov.fab.FloatingActionButton;

import java.util.List;

/**
 * Created by AdalbertoF on 13/08/2015.
 */
public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String TAREFAFRAGMENT_TAG = "TFTAG";

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
        mTarefasList.setSelector(R.drawable.item_tarefa_selected);

        mFabTarefa = (FloatingActionButton) findViewById(R.id.fabTarefa);

        if (!isTablet()) {
            mFabTarefa.attachToListView(mTarefasList);
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_tarefa, new TarefaViewFragment(), TAREFAFRAGMENT_TAG)
                    .commit();
        }

        // Eventos
        mTarefasList.setOnItemClickListener(this);
        mFabTarefa.setOnClickListener(this);
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
        setPrimeiraTarefa();
    }


    /**
     * Indica primeira tarefa ao iniciar o aplicativo.
     */
    private void setPrimeiraTarefa() {
        if(mTarefaSelecionada == null){
            mTarefasList.setItemChecked(0, true);
            mTarefaSelecionada = (Tarefas) mAdapter.getItem(0);
            visualizarTarefa(mTarefaSelecionada);
        }
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
            mTarefasList.setItemChecked(position, false);
            view.setSelected(false);
            showToolbarBottom(false);
            visualizarTarefa(null);
        } else {
            mTarefaSelecionada = mTarefas.get(position);
            visualizarTarefa(mTarefaSelecionada);
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


    /**
     * Mostra os dados da tarefa selecionada no fragment tarefa_container
     * nos dispositivos tablets.
     */
    private void visualizarTarefa(Tarefas tarefa) {
        if (isTablet()) {
            Fragment tarefaFragment = new TarefaViewFragment();

            Bundle args = new Bundle();
            args.putSerializable(TarefaFragment.EXTRA_TAREFA, tarefa);
            tarefaFragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_tarefa, tarefaFragment, TAREFAFRAGMENT_TAG)
                    .commit();
        }
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


    //@TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void showToolbarBottom(boolean show) {
        if (show) {
            if (!isTablet()) mFabTarefa.hide();
            mToolbarBottom.setVisibility(View.VISIBLE);
        } else {
            mToolbarBottom.setVisibility(View.INVISIBLE);
            if (!isTablet()) mFabTarefa.show();
        }
    }


    private List<Tarefas> buscarTarefas() {
        String filtro = "encerrado == 0";
        return new TarefasDAO(this).buscarTarefas(filtro);
    }

}
