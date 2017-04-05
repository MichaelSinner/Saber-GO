package com.example.michaelsinner.sabergo.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.example.michaelsinner.sabergo.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PruebaDiagnostico extends AppCompatActivity {

    private Button btnOpcionA;
    private Button btnOpcionB;
    private Button btnOpcionC;
    private Button btnOpcionD;
    private ImageButton imgBtnPregunta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prueba_diagnostico);

        imgBtnPregunta = (ImageButton) findViewById(R.id.imBtnPregunta);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038").into(imgBtnPregunta);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgBtnPregunta);
        photoViewAttacher.update();

    }
}
