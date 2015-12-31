package com.adalbertofjr.listatarefas.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adalbertofjr.listatarefas.R;
import com.adalbertofjr.listatarefas.dominio.Tarefas;

import java.util.List;

/**
 * Created by AdalbertoF on 28/08/2015.
 */
public class TarefaListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Tarefas> mTarefas;

    public TarefaListAdapter(Context mContext, List<Tarefas> mTarefas) {
        this.mContext = mContext;
        this.mTarefas = mTarefas;
    }


    @Override
    public int getCount() {
        return mTarefas.size();
    }


    @Override
    public Object getItem(int position) {
        return mTarefas.get(position);
    }


    @Override
    public long getItemId(int position) {
        return mTarefas.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;

        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_tarefa, null);

            viewHolder = new ViewHolder();
            viewHolder.corPrioridade = convertView.findViewById(R.id.vwCorPrioridade);
            viewHolder.titulo = (TextView) convertView.findViewById(R.id.txvTitulo);
            viewHolder.dataVencimento = (TextView) convertView.findViewById(R.id.txvDataVencimento);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Tarefas tarefa = mTarefas.get(position);
        int[] cores = mContext.getResources().getIntArray(R.array.corPrioridade);
        int prioridade = tarefa.getPrioridade() - 1;

        viewHolder.titulo.setText(tarefa.getTitulo());
        viewHolder.dataVencimento.setText(tarefa.getDataVencimento());
        viewHolder.corPrioridade.setBackgroundColor(cores[(prioridade)]);

        return convertView;
    }

    public void remove(Tarefas mTarefaSelecionada) {
        mTarefas.remove(mTarefaSelecionada);
    }

    static class ViewHolder{
        View corPrioridade;
        TextView titulo;
        TextView dataVencimento;
    }
}
