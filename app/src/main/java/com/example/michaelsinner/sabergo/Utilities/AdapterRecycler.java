package com.example.michaelsinner.sabergo.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michaelsinner.sabergo.R;

/**
 * Created by Michael Sinner on 15/6/2017.
 */

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder>
{
    private String[] data;

    public AdapterRecycler(String[] list)
    {
        this.data = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta_diaria, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvPreguntaDiaria.setText(data[position]);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView tvPreguntaDiaria;

        public ViewHolder(View itemView) {
            super(itemView);
            //tvPreguntaDiaria = (TextView) itemView.findViewById(R.id.tvPreguntaTitulo);

        }
    }
}
