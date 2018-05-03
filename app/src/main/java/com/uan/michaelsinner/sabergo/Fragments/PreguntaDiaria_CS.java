package com.uan.michaelsinner.sabergo.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.R;
import com.uan.michaelsinner.sabergo.Utilities.AdapterQuestion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreguntaDiaria_CS extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Question> list;
    private AdapterQuestion adapter;
    private DatabaseReference databaseReference;
    private Random random;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        loadDatos();
        super.onCreate(savedInstanceState);
    }

    public PreguntaDiaria_CS() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_pregunta_diaria__cs, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_pregunta_CS);
        LinearLayoutManager ln = new LinearLayoutManager(getActivity());
        ln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ln);

        //loadDatos();
        iniciarAdaptador();
        //recyclerView.setBackgroundColor(R.drawable.btn_frag_cs);
        recyclerView.setAdapter(adapter);

        return v;
    }

    public void loadDatos() {
        random = new Random();
        list = new ArrayList<>();
        int num = 10;


        DatabaseReference refQuestions = databaseReference.child("Question");

        /*
        Query query = refQuestions.orderByChild("area").equalTo("CS").limitToLast(6);
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
        */
        for(int i = 0; i<=4;i++)
        {
            refQuestions.orderByChild("area").equalTo("CS").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int questionCount = (int) dataSnapshot.getChildrenCount();
                    int rand = random.nextInt(questionCount);
                    Iterator itr = dataSnapshot.getChildren().iterator();
                    // Log.e(TAG, " "+questionCount+"   "+rand);

                    for(int i = 0; i < rand; i++) {
                        itr.next();
                    }
                    DataSnapshot childSnapshot = (DataSnapshot) itr.next();
                    Question questionAdd = childSnapshot.getValue(Question.class);
                    questionAdd.setQuestionKey(childSnapshot.getKey());

                    //Log.e(TAG, questionAdd.toString());

                    list.add(questionAdd);


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }

    public void iniciarAdaptador() {
        adapter = new AdapterQuestion(list);
    }

}
