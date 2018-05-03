package com.uan.michaelsinner.sabergo.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.uan.michaelsinner.sabergo.Activities.PreguntaDiaria;
import com.uan.michaelsinner.sabergo.Data.ImageWheel;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;
import com.uan.michaelsinner.sabergo.Utilities.AdapterImageWheel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


import github.hellocsl.cursorwheel.CursorWheelLayout;

public class PreguntaDiaria_RN extends Fragment implements  CursorWheelLayout.OnMenuSelectedListener {

    CursorWheelLayout wheel;
    List<ImageWheel> listImageWheel;
    Button btnResponder;
    private Question question;
    private DatabaseReference mDatabase;
    private Random random;
    private User currentUser;
    private Bundle extras;


    public PreguntaDiaria_RN() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pregunta_diaria__rn, container, false);
        initView(view);
        loadData();
        wheel.setOnMenuSelectedListener(this);



        btnResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toPregunta(question,view.getContext(),currentUser);
            }
        });
        return view;

    }

    private void initView(View view) {
        wheel = (CursorWheelLayout) view.findViewById(R.id.wheelRN);
        btnResponder = (Button) view.findViewById(R.id.btnResponderRand);
        extras = ((Activity) view.getContext()).getIntent().getExtras();
    }

    private void loadData(){
        String stringUser;
        listImageWheel = new ArrayList<>();
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_cs,"CS"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_cn,"CN"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_in,"IN"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_mt,"MT"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_lc,"LC"));
        AdapterImageWheel adapter = new AdapterImageWheel(getContext(),listImageWheel);
        wheel.setAdapter(adapter);


        stringUser = extras.getString("USER");
        currentUser = new Gson().fromJson(stringUser, User.class);



    }

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
        if(parent.getId() == R.id.wheelRN ){
            Toast.makeText(getContext(),listImageWheel.get(pos).imageDescription,Toast.LENGTH_SHORT).show();
            getQuestion(listImageWheel.get(pos).imageDescription);
        }
    }

    public void getQuestion(String area){
        random = new Random();

        DatabaseReference refQuestions = mDatabase.child("Question");

        refQuestions.orderByChild("area").equalTo(area).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int questionCount = (int) dataSnapshot.getChildrenCount();
                //int questionCount = 155;
                int rand = random.nextInt(questionCount);
                Iterator itr = dataSnapshot.getChildren().iterator();


                for(int i = 0; i < rand; i++) {
                    itr.next();
                }
                DataSnapshot childSnapshot = (DataSnapshot) itr.next();
                question = childSnapshot.getValue(Question.class);
                question.setQuestionKey(childSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
