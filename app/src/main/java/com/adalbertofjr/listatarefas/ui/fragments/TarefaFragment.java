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

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.adapter.PrioridadeAdapter;
import com.adalbertofjr.listatarefas.dao.TarefasDAO;
import com.adalbertofjr.listatarefas.dominio.Tarefas;
import com.melnykov.fab.FloatingActionButton;

import java.util.Calendar;

/**
 * Created by AdalbertoF on 19/08/2015.
 */
public class TarefaFragment extends Fragment implements View.OnClickListener {


    public static final String EXTRA_TAREFA = "tarefa";
    public static final String TAG_DETALHE = "tag_detalhe";
    private Toolbar mToolbar;
    private Tarefas mTarefa;
    private EditText mTitulo;
    private Button mDataVencimento;
    private Spinner mPrioridade;
    private EditText mObs;
    private FloatingActionButton mFabPronto;
    private PrioridadeAdapter mAdapterPrioridade;

    public static TarefaFragment novaTarefa(Tarefas tarefa) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_TAREFA, tarefa);

        TarefaFragment fragment = new TarefaFragment();
        fragment.setArguments(parametros);

        return fragment;
    }

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
        View layout = inflater.inflate(R.layout.fragment_detalhe_tarefa, container, false);

        mTitulo = (EditText) layout.findViewById(R.id.edtTitulo);
        mDataVencimento = (Button) layout.findViewById(R.id.btnDataVencimento);
        mPrioridade = (Spinner) layout.findViewById(R.id.spnPrioridade);
        mObs = (EditText) layout.findViewById(R.id.edtObs);
        mFabPronto = (FloatingActionButton) layout.findViewById(R.id.fabPronto);

        carregarValoresPrioridade();

        if (mTarefa != null) {
            editarTarefa();
        }

        mDataVencimento.setOnClickListener(this);
        if (mFabPronto != null) mFabPronto.setOnClickListener(this);

        return layout;
    }

    private void editarTarefa() {
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
        int viewId = v.getId();

        if (viewId == R.id.btnDataVencimento) {
            showDatePickerDialog(v);
        }

        if (viewId == R.id.fabPronto) {
            salvarTarefa();
        }
    }

    private void salvarTarefa() {
        if(verificarDados()){
            Tarefas tarefa = new Tarefas();

            if(mTarefa != null) tarefa.setId(mTarefa.getId());

            tarefa.setTitulo(mTitulo.getText().toString());
            tarefa.setDataVencimento(mDataVencimento.getText().toString());
            tarefa.setPrioridade(Integer.valueOf(mPrioridade.getSelectedItem().toString()));
            tarefa.setObservacao(mObs.getText().toString());

            TarefasDAO tarefasDAO = new TarefasDAO(getActivity());

            long result = tarefasDAO.salvar(tarefa);

            if(result > 0){
                getActivity().finish();
            }
        }
    }


    private boolean verificarDados(){
        if(TextUtils.isEmpty(mTitulo.getText())){
            mTitulo.setError("Tarefa vazia");
            return false;
        }
        return true;
    }

    /**
     * Para seleciona Data Prioridade
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Button btnDataVencimento = (Button) getActivity().findViewById(R.id.btnDataVencimento);
            btnDataVencimento.setText(day + "/" + (month + 1) + "/" + year);
        }
    }

}
