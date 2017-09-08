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

/**
 * A simple {@link Fragment} subclass.
 */
public class PreguntaDiaria_CS extends Fragment {

    private RecyclerView recyclerView;
    private ArrayList<Question> list;
    private AdapterQuestion adapter;


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

        loadDatos();
        iniciarAdaptador();
        recyclerView.setAdapter(adapter);

        return v;
    }

    public void loadDatos(){
        list = new ArrayList<>();
        list.add(new Question(78,"CS"));
        list.add(new Question(123,"CS"));
        list.add(new Question(111,"FF"));
        list.add(new Question(89,"CS"));
        list.add(new Question(14,"FF"));
    }

    public void iniciarAdaptador(){
        adapter = new AdapterQuestion(list);
    }

}
