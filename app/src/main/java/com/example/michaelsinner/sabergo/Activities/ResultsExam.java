package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.michaelsinner.sabergo.R;

public class ResultsExam extends AppCompatActivity
{

    private Button btnVolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_exam);

        btnVolver = (Button) findViewById(R.id.btnVolverMenu);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMainMenu();
            }
        });


    }

    public void toMainMenu()
    {
        Intent toMainMenu = new Intent(this , MainMenu.class);

        toMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMainMenu);
    }
}
