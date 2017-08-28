package com.example.michaelsinner.sabergo.Activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

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
    private Question question;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;


    private int counterTime;
    private int selectedAnswer;
    private int numPregunta = 0;

    private DatabaseReference mDatabase;
    private static final String TAG ="error";


    private ArrayList<Question> questionLista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prueba_diagnostico);
        relativeLayout = (RelativeLayout) findViewById(R.id.relativeExmDiag);

        tvNumQuest = (TextView) findViewById(R.id.tvnumQuest);
        tvTime = (TextView) findViewById(R.id.tvTime);

        btnOpcionA = (Button) findViewById(R.id.btnOpcionA);
        btnOpcionB = (Button) findViewById(R.id.btnOpcionB);
        btnOpcionC = (Button) findViewById(R.id.btnOpcionC);
        btnOpcionD = (Button) findViewById(R.id.btnOpcionD);
        btnSendAnswer = (Button) findViewById(R.id.btnSendAnswer);

        imgBtnQuestion = (ImageButton) findViewById(R.id.imgQuestion);



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

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rfeQuestCompArea = mDatabase.child("QuestCompArea").child("CN");
        final DatabaseReference rfeQuest = mDatabase.child("Question");

        rfeQuestCompArea.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message: dataSnapshot.getChildren())
                {
                    String keyPregunta = message.getKey();
                    long value = (long) message.getValue();
                    //DatabaseReference cases = mDatabase.child("Questions");

                    rfeQuest.orderByChild("questionID").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                          if(dataSnapshot.exists()){
                                boolean b = dataSnapshot.getKey().isEmpty();
                                Question pregunta = dataSnapshot.getValue(Question.class);
                                //String info = String.valueOf(dataSnapshot.getValue());
                                pregunta.getQuestionID();
                                //list.add(pregunta);
                                Log.e(TAG,"infoss : "+pregunta.getQuestionID()+" : "+b);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //String jum = message.child(keyPregunta).toString();

                    Log.e(TAG,"infos : "+keyPregunta+" : "+value);

                    //System.out.print(idPregunta);
                    //pregunta = message.getValue(Question.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


/*
        mDatabase = FirebaseDatabase.getInstance().getReference().child("QuestCompArea").child("CN");
        final String keyPreguntas = mDatabase.getKey();

        final String numbers[] = new String[10-1];




        mDatabase.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message: dataSnapshot.getChildren())
                {
                    String keyPregunta = message.getKey();
                    DatabaseReference prueba = mDatabase.child(keyPregunta);
                    String pruebaS = String.valueOf(message.getValue()) ;


                    String jum = message.child(keyPregunta).toString();
                    //String idPregunta =  message.child(keyPregunta)
                    Log.e(TAG,"infos : "+keyPregunta+" : "+jum+" : "+pruebaS);

                    //System.out.print(idPregunta);
                    //pregunta = message.getValue(Question.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
*/
        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038
        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100002.PNG?alt=media&token=7a95d633-e45c-45f5-a477-8c502ce5397f



    }

    public void doExamDiagnostic(ArrayList<Question> questions){

    }

    private ArrayList<Question> createExam()
    {
        ArrayList<Question> questionCN = new ArrayList<>();
        ArrayList<Question> questionCS = new ArrayList<>();
        ArrayList<Question> questionLC = new ArrayList<>();
        ArrayList<Question> questionMT = new ArrayList<>();
        ArrayList<Question> questionIN = new ArrayList<>();
        ArrayList<Question> arrayList = new ArrayList<>();

        questionCN = getQuestions("CN", 12);
        questionCS = getQuestions("CS", 10);
        questionLC = getQuestions("LC", 8);
        questionMT = getQuestions("MT", 10);
        questionIN = getQuestions("IN", 10);





        return arrayList;

    }


    private ArrayList<Question> getQuestions(String idArea, int tamArray)
    {
        final ArrayList<Question> list = new ArrayList<>();


        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference rfeQuestCompArea = mDatabase.child("QuestCompArea").child(idArea);
        final DatabaseReference rfeQuest = mDatabase.child("Question");

        rfeQuestCompArea.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message: dataSnapshot.getChildren())
                {
                    String keyPregunta = message.getKey();
                    long value = (long) message.getValue();
                    //DatabaseReference cases = mDatabase.child("Questions");

                    rfeQuest.orderByChild("questionID").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {
                                Question pregunta = dataSnapshot.getValue(Question.class);
                                list.add(pregunta);
                                //String info = String.valueOf(dataSnapshot.getValue());
                                Log.e(TAG,"infoss : "+pregunta.getQuestionID()+" : ");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    //String jum = message.child(keyPregunta).toString();

                    Log.e(TAG,"infos : "+keyPregunta+" : "+value);

                    //System.out.print(idPregunta);
                    //pregunta = message.getValue(Question.class);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        for(int i=0; i<tamArray-1; i++)
        {
            int random = new Random().nextInt(list.size())+1;



        }



        return list;

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
