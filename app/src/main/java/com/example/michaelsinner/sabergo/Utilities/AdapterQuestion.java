package com.example.michaelsinner.sabergo.Utilities;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;

import java.util.ArrayList;

/**
 * Created by Michael Sinner on 5/9/2017.
 */

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.AdapterQuiestionViewHolder> {

    private ArrayList<Question> list;

    public AdapterQuestion(ArrayList<Question> list) {
        this.list = list;
    }

    @Override
    public AdapterQuiestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterQuiestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta_diaria, parent,false));
    }

    @Override
    public void onBindViewHolder(AdapterQuiestionViewHolder holder, int position) {
        Question question = list.get(position);
        holder.tvIDQUest.setText("MT000"+String.valueOf(question.getQuestionID()));
        holder.tvArea.setText("Área : "+question.getQuestionArea());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AdapterQuiestionViewHolder extends RecyclerView.ViewHolder{

        TextView tvIDQUest, tvArea;
        int expGained = 1;
        int fragGained = 2;
        public AdapterQuiestionViewHolder(View itemView) {
            super(itemView);

            tvIDQUest = (TextView) itemView.findViewById(R.id.tvIDQuest_PD);

            tvArea = (TextView) itemView.findViewById(R.id.tvArea_PD);

        }
    }
}
