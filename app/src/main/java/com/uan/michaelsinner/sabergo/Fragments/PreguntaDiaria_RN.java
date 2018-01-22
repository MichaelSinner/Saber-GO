package com.uan.michaelsinner.sabergo.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.uan.michaelsinner.sabergo.Data.ImageWheel;
import com.uan.michaelsinner.sabergo.R;
import com.uan.michaelsinner.sabergo.Utilities.AdapterImageWheel;

import java.util.ArrayList;
import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class PreguntaDiaria_RN extends Fragment implements  CursorWheelLayout.OnMenuSelectedListener {

    CursorWheelLayout wheel;
    List<ImageWheel> listImageWheel;


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
        return view;


    }

    private void initView(View view) {
        wheel = (CursorWheelLayout) view.findViewById(R.id.wheelRN);
    }

    private void loadData(){
        listImageWheel = new ArrayList<>();
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_cs,"Pregunta de Ciencias Sociales"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_cn,"Pregunta de Ciencias Naturales"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_in,"Pregunta de Inglés"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_mt,"Pregunta de Matemáticas"));
        listImageWheel.add(new ImageWheel(R.drawable.ic_wheel_lc,"Pregunta de Lectura Crítica"));
        AdapterImageWheel adapter = new AdapterImageWheel(getContext(),listImageWheel);
        wheel.setAdapter(adapter);



    }

    @Override
    public void onItemSelected(CursorWheelLayout parent, View view, int pos) {
        if(parent.getId() == R.id.wheelRN ){
            Toast.makeText(getContext(),listImageWheel.get(pos).imageDescription,Toast.LENGTH_SHORT).show();
        }
    }
}
