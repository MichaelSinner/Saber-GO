package com.example.michaelsinner.sabergo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity
{

    Button btnStart;
    TextView tvInicio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        /*
        * se referencia la parte logica y visual del Button btnStart y se implenta su evento del click
        * */
        //Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Sanlabello.ttf");
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                startActivity(toIndex());
            }
        });
        //btnStart.setTypeface(font);



    }

    public Intent toIndex()
    {
        Intent toIndex = new Intent(MainActivity.this, Index.class);
        return toIndex;
    }
}
