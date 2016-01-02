package com.adalbertofjr.listatarefas.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.adapter.PrioridadeAdapter;
import com.adalbertofjr.listatarefas.dao.TarefasDAO;
import com.adalbertofjr.listatarefas.dominio.Tarefas;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

/**
 * Created by AdalbertoF on 19/08/2015.
 */
public class TarefaViewFragment extends Fragment implements View.OnClickListener {


    public static final String EXTRA_TAREFA = "tarefa";
    private Tarefas mTarefa;
    private TextView mTitulo;
    private TextView mDataVencimento;
    private Spinner mPrioridade;
    private TextView mObs;
    private PrioridadeAdapter mAdapterPrioridade;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null){
            mTarefa = (Tarefas) getArguments().getSerializable(EXTRA_TAREFA);
        }

        setHasOptionsMenu(true);
    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_detalhe_tarefa_view, container, false);

        mTitulo = (TextView) layout.findViewById(R.id.txvTitulo);
        mDataVencimento = (TextView) layout.findViewById(R.id.txvDataVencimento);
        mPrioridade = (Spinner) layout.findViewById(R.id.spnPrioridade);
        mObs = (TextView) layout.findViewById(R.id.txvObs);

        carregarValoresPrioridade();

        if (mTarefa != null) {
            visualizarTarefa();
        }

        mDataVencimento.setOnClickListener(this);

        return layout;
    }

    private void visualizarTarefa() {
        mTitulo.setText(mTarefa.getTitulo());
        mPrioridade.setSelection((int) mAdapterPrioridade.getItemId(mTarefa.getPrioridade()));
        mDataVencimento.setText(mTarefa.getDataVencimento());
        mObs.setText(mTarefa.getObservacao());
    }

    private void carregarValoresPrioridade() {
        String[] valores = getActivity().getResources().getStringArray(R.array.prioridade);
        mAdapterPrioridade = new PrioridadeAdapter(getActivity(), valores);
        mPrioridade.setAdapter(mAdapterPrioridade);
    }


    @Override
    public void onClick(View v) {

    }
}
