package com.example.michaelsinner.sabergo;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Index extends Activity
{
    Button btnprueba;
    TextView tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);



        ActionBar actionBar = getActionBar();


        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);



        ImageView ivExit = (ImageView) customView.findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                System.exit(0);
            }
        });

        btnprueba = (Button) findViewById(R.id.btnLogin);
        btnprueba.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intetn = new Intent(Index.this, Login.class);
                startActivity(intetn);
            }
        });



    }
}
