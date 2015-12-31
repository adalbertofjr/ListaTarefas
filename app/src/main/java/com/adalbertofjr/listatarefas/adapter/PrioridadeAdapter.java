package com.adalbertofjr.listatarefas.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.adalbertofjr.listatarefas.R;

/**
 * Created by AdalbertoF on 28/08/2015.
 */
public class PrioridadeAdapter extends BaseAdapter {

    private Context mContext;
    private String[] mPrioridades;

    public PrioridadeAdapter(Context mContext, String[] mPrioridades) {
        this.mContext = mContext;
        this.mPrioridades = mPrioridades;
    }

    @Override
    public int getCount() {
        return mPrioridades.length;
    }

    @Override
    public Object getItem(int position) {
        return mPrioridades[position];
    }

    @Override
    public long getItemId(int position) {
        return position - 1;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_prioridade, null);

            viewHolder = new ViewHolder();
            viewHolder.corPrioridade = (View) convertView.findViewById(R.id.vwCorPrioridade);
            viewHolder.prioridade = (TextView) convertView.findViewById(R.id.txvPrioridade);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        int[] cores = mContext.getResources().getIntArray(R.array.corPrioridade);

        String prioridade = mPrioridades[position];
        viewHolder.prioridade.setText(prioridade);
        viewHolder.corPrioridade.setBackgroundColor(cores[position]);

        return convertView;
    }

    static class ViewHolder {
        View corPrioridade;
        TextView prioridade;
    }
}
