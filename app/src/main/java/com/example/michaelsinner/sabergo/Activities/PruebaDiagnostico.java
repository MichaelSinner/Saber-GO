package com.example.michaelsinner.sabergo.Activities;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.michaelsinner.sabergo.Data.Pregunta;
import com.example.michaelsinner.sabergo.R;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PruebaDiagnostico extends AppCompatActivity {

    private ImageButton imgBtnQuestion;
    private PopupWindow popAnswerTrue;
    private PopupWindow popAnswerfalse;
    private Button btnOpcionA;
    private Button btnOpcionB;
    private Button btnOpcionC;
    private Button btnOpcionD;
    private Button btnSendAnswer;
    private TextView tvTime;
    private TextView tvNumQuest;
    private com.example.michaelsinner.sabergo.Data.PruebaDiagnostico prueba;
    private Pregunta pregunta;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;


    private int counterTime;
    private int selectedAnswer;
    private int numPregunta = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prueba_diagnostico);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeExmDiag);

        tvNumQuest = (TextView) findViewById(R.id.tvnumQuest);




        /*
        imgBtnPregunta = (ImageButton) findViewById(R.id.imgQuestion);
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038").into(imgBtnPregunta);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgBtnPregunta);
        photoViewAttacher.update();


        tvTime = (TextView) findViewById(R.id.tvTime);
        counterTime = 0;
        countDownTimer = new CountDownTimer(10000,100) {
            @Override
            public void onTick(long l) {
                tvTime.setText(String.valueOf(counterTime));
                counterTime++;
            }

            @Override
            public void onFinish() {

            }
        }.start();
*/
        int seleccion = 0;


        selectedAnswer = 0;
        counterTime = 0;

        tvTime = (TextView) findViewById(R.id.tvTime);
        btnOpcionA = (Button) findViewById(R.id.btnOpcionA);
        btnOpcionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionA.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.GRAY);
                btnOpcionC.setBackgroundColor(Color.GRAY);
                btnOpcionD.setBackgroundColor(Color.GRAY);
                selectedAnswer = 1;

            }
        });
        btnOpcionB = (Button) findViewById(R.id.btnOpcionB);
        btnOpcionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionB.setBackgroundColor(Color.GREEN);
                btnOpcionA.setBackgroundColor(Color.GRAY);
                btnOpcionC.setBackgroundColor(Color.GRAY);
                btnOpcionD.setBackgroundColor(Color.GRAY);
                selectedAnswer = 2;

            }
        });
        btnOpcionC = (Button) findViewById(R.id.btnOpcionC);
        btnOpcionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionC.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.GRAY);
                btnOpcionA.setBackgroundColor(Color.GRAY);
                btnOpcionD.setBackgroundColor(Color.GRAY);
                selectedAnswer = 3;

            }
        });
        btnOpcionD = (Button) findViewById(R.id.btnOpcionD);
        btnOpcionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionD.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.GRAY);
                btnOpcionC.setBackgroundColor(Color.GRAY);
                btnOpcionA.setBackgroundColor(Color.GRAY);
                selectedAnswer = 4;
                Toast.makeText(getApplicationContext(), "D",Toast.LENGTH_LONG).show();
            }
        });
        btnSendAnswer = (Button) findViewById(R.id.btnSendAnswer);
        btnSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedAnswer == 0){
                    btnSendAnswer.setError("Selecciona una respuesta");
                }else{
                    numPregunta++;

                    if(correctAnswer(selectedAnswer)){
                        //pop correcto
                        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_answertrue,null);

                        popAnswerTrue = new PopupWindow(container,320,480,true);
                        popAnswerTrue.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,320,480);
                        container.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                popAnswerTrue.dismiss();
                                return true;
                            }
                        });

                    }else{
                        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.pop_answerfalse,null);

                        popAnswerfalse = new PopupWindow(container,320,480,true);
                        popAnswerfalse.showAtLocation(relativeLayout, Gravity.NO_GRAVITY,320,480);
                        container.setOnTouchListener(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View view, MotionEvent motionEvent) {
                                popAnswerfalse.dismiss();
                                return true;
                            }
                        });

                    }
                    selectedAnswer = 0;
                }
                ActualizarPregunta(numPregunta);


            }
        });

        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038
        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100002.PNG?alt=media&token=7a95d633-e45c-45f5-a477-8c502ce5397f
        imgBtnQuestion = (ImageButton) findViewById(R.id.imgQuestion);


    }

    private boolean correctAnswer(int seleccion) {

        if(seleccion == 1){
            return false;
        }else {
            return true;
        }

    }

    private void ActualizarPregunta(int numPregunta){

        int number = 02;
        Glide.with(this).load("https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038").into(imgBtnQuestion);
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgBtnQuestion);
        photoViewAttacher.update();
        tvNumQuest.setText(String.valueOf(numPregunta)+"/50");

        btnOpcionD.setBackgroundColor(Color.GRAY);
        btnOpcionB.setBackgroundColor(Color.GRAY);
        btnOpcionC.setBackgroundColor(Color.GRAY);
        btnOpcionA.setBackgroundColor(Color.GRAY);


    }
}
