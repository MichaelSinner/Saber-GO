package com.uan.michaelsinner.sabergo.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uan.michaelsinner.sabergo.Activities.PreguntaDiaria;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;

import java.util.ArrayList;

/**
 * Created by Michael Sinner on 5/9/2017.
 */

public class AdapterQuestion extends RecyclerView.Adapter<AdapterQuestion.AdapterQuiestionViewHolder> {

    private ArrayList<Question> list;
    private Context context;

    private User currentUser;
    private Bundle extras;

    public AdapterQuestion(ArrayList<Question> list) {
        this.list = list;
    }

    @Override
    public AdapterQuiestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AdapterQuiestionViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pregunta_diaria, parent, false));
    }

    @Override
    public void onBindViewHolder(AdapterQuiestionViewHolder holder, int position) {
        Question question = list.get(position);
        holder.tvIDQUest.setText(question.getArea()+"000" + String.valueOf(question.getQuestionID()));
        holder.tvArea.setText("Área : " + question.getArea());
        holder.tvPuntosFRG_PD.setText("+ 1 "+question.getArea());

        if(question.getArea().equals("CN")){
            holder.imQuest.setBackgroundResource(R.drawable.ic_wheel_cn);

        }else if(question.getArea().equals("CS")){
            holder.imQuest.setBackgroundResource(R.drawable.ic_wheel_cs);
        }else if (question.getArea().equals("IN")){
            holder.imQuest.setBackgroundResource(R.drawable.ic_wheel_in);
        }else if (question.getArea().equals("MT")){
            holder.imQuest.setBackgroundResource(R.drawable.ic_wheel_mt);
        }else if(question.getArea().equals("LC")){
            holder.imQuest.setBackgroundResource(R.drawable.ic_wheel_lc);

        }


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class AdapterQuiestionViewHolder extends RecyclerView.ViewHolder {

        TextView tvIDQUest, tvArea, tvPuntosFRG_PD;
        ImageView imQuest;
        RelativeLayout layout;
        //public Context context;
        int expGained = 1;
        int fragGained = 2;

        public AdapterQuiestionViewHolder(View itemView) {
            super(itemView);

            String stringUser;

            //this.context = context;

            tvIDQUest = (TextView) itemView.findViewById(R.id.tvIDQuest_PD);
            tvArea = (TextView) itemView.findViewById(R.id.tvArea_PD);
            tvPuntosFRG_PD = (TextView) itemView.findViewById(R.id.tvPuntosFRG_PD);
            imQuest = (ImageView) itemView.findViewById(R.id.ivPregunta_PD);
            layout = (RelativeLayout) itemView.findViewById(R.id.relativePD);

            context = itemView.getContext();

            extras = ((Activity) context).getIntent().getExtras();
            stringUser = extras.getString("USER");
            currentUser = new Gson().fromJson(stringUser, User.class);



            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION)
                    {
                        Question clickedQUestion = list.get(pos);
                        toPregunta(clickedQUestion, context, currentUser);

                    }
                }
            });

        }

        public void toPregunta(final Question question, Context context, User user) {

            final Intent toPregunta = new Intent(context, PreguntaDiaria.class);
            toPregunta.putExtra("QUESTION", new Gson().toJson(question));
            toPregunta.putExtra("USER", new Gson().toJson(user));



            toPregunta.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(toPregunta);
        }

    }
}
