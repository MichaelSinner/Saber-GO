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

import java.util.ArrayList;


public class PreguntaDiaria_IN extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Question> list;
    private AdapterQuestion adapter;


    public PreguntaDiaria_IN() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pregunta_diaria__in, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_pregunta_IN);
        LinearLayoutManager ln = new LinearLayoutManager(getActivity());
        ln.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(ln);

        loadDatos();
        iniciarAdaptador();
        recyclerView.setAdapter(adapter);
        return v;
    }

    public void loadDatos(){
        list = new ArrayList<>();
        list.add(new Question(110,"IN"));
        list.add(new Question(135,"IN"));

    }

    public void iniciarAdaptador(){
        adapter = new AdapterQuestion(list);
    }

}
