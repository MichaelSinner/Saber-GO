package com.uan.michaelsinner.sabergo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.uan.michaelsinner.sabergo.Activities.Index;
import com.uan.michaelsinner.sabergo.Utilities.GeneradorExmDiagno;
import com.uan.michaelsinner.sabergo.Utilities.TextFade;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;




public class MainActivity extends Activity {


    Button btnStart;
    ImageView imNube01, imNube02;
    TextView tvInicio;
    GeneradorExmDiagno generador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(com.uan.michaelsinner.sabergo.R.layout.activity_main);

        imNube01 = (ImageView) findViewById(com.uan.michaelsinner.sabergo.R.id.ivNube00);
        imNube01.setVisibility(View.VISIBLE);
        imNube02 = (ImageView) findViewById(com.uan.michaelsinner.sabergo.R.id.ivNube01);
        imNube02.setVisibility(View.VISIBLE);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");
        tvInicio = (TextView) findViewById(com.uan.michaelsinner.sabergo.R.id.tvSubtitle);
        tvInicio.setTypeface(font);

        new TextFade(getBaseContext(), tvInicio);

        //printHashKey();

        //  generador = new GeneradorExmDiagno(50,10,8,7,12,10,3);

        /*
        * se referencia la parte logica y visual del Button btnStart y se implenta su evento del click
        * */
        btnStart = (Button) findViewById(com.uan.michaelsinner.sabergo.R.id.btnStart);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toIndex());
            }
        });
        btnStart.setTypeface(font);


    }

    public void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.michaelsinner.sabergo", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("SHA: ", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public Intent toIndex() {
        Intent toIndex = new Intent(MainActivity.this, Index.class);
        return toIndex;
    }
}
