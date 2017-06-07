package com.example.michaelsinner.sabergo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.michaelsinner.sabergo.Data.Achievments;
import com.example.michaelsinner.sabergo.R;

public class Achievements extends AppCompatActivity {

    private Achievments data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
    }
}
