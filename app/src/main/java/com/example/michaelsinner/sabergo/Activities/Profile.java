package com.example.michaelsinner.sabergo.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.michaelsinner.sabergo.R;

public class Profile extends AppCompatActivity {

    private TextView mTextMessage;
    private com.example.michaelsinner.sabergo.Data.Profile profile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


    }

}
