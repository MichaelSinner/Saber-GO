package com.example.michaelsinner.sabergo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;
import com.example.michaelsinner.sabergo.Utilities.AdapterQuestion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PreguntaDiaria_IN extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Question> list;
    private AdapterQuestion adapter;
    private DatabaseReference databaseReference;

    public PreguntaDiaria_IN() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {


        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadDatos();

        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pregunta_diaria__in, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_pregunta_IN);
        LinearLayoutManager ln = new LinearLayoutManager(getActivity());
        ln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ln);

        //loadDatos();
        iniciarAdaptador();
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void loadDatos() {
        list = new ArrayList<>();
        int num = 10;


        DatabaseReference refQuestions = databaseReference.child("Question");

        Query query = refQuestions.orderByChild("area").equalTo("IN").limitToLast(3);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    for(DataSnapshot quest: dataSnapshot.getChildren()){
                        Question question = quest.getValue(Question.class);
                        question.setQuestionKey(quest.getKey());
                        list.add(question);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        query = null;


    }

    public void iniciarAdaptador() {
        adapter = new AdapterQuestion(list);
    }

}
