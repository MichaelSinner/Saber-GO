package com.example.michaelsinner.sabergo.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;
import com.example.michaelsinner.sabergo.Utilities.AdapterQuestion;

import java.util.ArrayList;


public class PreguntaDiaria_MT extends Fragment {


    private RecyclerView recyclerView;
    private ArrayList<Question> list;
    private AdapterQuestion adapter;

    public PreguntaDiaria_MT() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_pregunta_diaria__mt, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.rv_pregunta_MT);
        LinearLayoutManager ln = new LinearLayoutManager(getActivity());
        ln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ln);

        loadDatos();
        iniciarAdaptador();
        recyclerView.setAdapter(adapter);

        //adapter = new AdapterQuestion(list);
        //recyclerView.setAdapter(adapter);

        return v;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void loadDatos(){
        list = new ArrayList<>();
        list.add(new Question(2,"MT"));
        list.add(new Question(3,"MT"));
        list.add(new Question(4,"MT"));
        list.add(new Question(26,"MT"));
        list.add(new Question(22,"MT"));
    }

    public void iniciarAdaptador(){
        adapter = new AdapterQuestion(list);
    }
}
