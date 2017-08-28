package com.example.michaelsinner.sabergo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;
import com.example.michaelsinner.sabergo.Utilities.FindQuestion;

public class ModuloAR extends AppCompatActivity {
    private Question question;
    private FindQuestion util;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modulo_ar);
    }
}
