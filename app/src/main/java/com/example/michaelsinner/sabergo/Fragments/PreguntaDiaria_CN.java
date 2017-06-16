package com.example.michaelsinner.sabergo.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.michaelsinner.sabergo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PreguntaDiaria_CN extends Fragment {


    public PreguntaDiaria_CN() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pregunta_diaria__cn, container, false);
    }

}
