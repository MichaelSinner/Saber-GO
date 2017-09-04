package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.michaelsinner.sabergo.Data.Exam;
import com.example.michaelsinner.sabergo.Data.Question;
import com.example.michaelsinner.sabergo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PruebaDiagnostico extends AppCompatActivity
{
    private RelativeLayout relativeLayout;
    private ImageButton imgBtnQuestion;
    private PopupWindow popAnswerTrue;
    private PopupWindow popAnswerfalse;
    private Button btnOpcionA;
    private Button btnOpcionB;
    private Button btnOpcionC;
    private Button btnOpcionD;
    private Button btnSendAnswer;
    private Button btnIniciarExamen;
    private TextView tvTime;
    private TextView tvNumQuest;
    private TextView tvIDQuest;

    private Exam prueba;
    private Question question;

    private int counterTime;
    private int selectedAnswer;
    private int numPregunta = 0;
    private int counterQuestion;

    private  int numAnswerTrue;
    private  int numRight_LC;
    private  int numRight_MT;
    private  int numRight_CS;
    private  int numRight_CN;
    private  int numRight_IN;

    private final int TOTAL_QUEST_CN = 12;
    private final int TOTAL_QUEST_LC = 8;
    private final int TOTAL_QUEST_CS = 10;
    private final int TOTAL_QUEST_MT = 10;
    private final int TOTAL_QUEST_IN = 10;

    private DatabaseReference mDatabase;
    private static final String TAG = "error";


    private ArrayList<Question> question_All;
    private ArrayList<Question> questionCN_list;
    private ArrayList<Question> questionMT_list;
    private ArrayList<Question> questionCS_list;
    private ArrayList<Question> questionIN_list;
    private ArrayList<Question> questionLC_list;

    private Random random;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_prueba_diagnostico);

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeExmDiag);
        tvNumQuest = (TextView) findViewById(R.id.tvnumQuest);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvIDQuest = (TextView) findViewById(R.id.tvIdQuestion);
        btnOpcionA = (Button) findViewById(R.id.btnOpcionA);
        btnOpcionB = (Button) findViewById(R.id.btnOpcionB);
        btnOpcionC = (Button) findViewById(R.id.btnOpcionC);
        btnOpcionD = (Button) findViewById(R.id.btnOpcionD);
        btnIniciarExamen = (Button) findViewById(R.id.btnIniciarExamen);
        btnSendAnswer = (Button) findViewById(R.id.btnSendAnswer);
        imgBtnQuestion = (ImageButton) findViewById(R.id.imgQuestion);

        questionCN_list = new ArrayList<>();
        questionCS_list = new ArrayList<>();
        questionMT_list = new ArrayList<>();
        questionLC_list = new ArrayList<>();
        questionIN_list = new ArrayList<>();

        boolean beginExamen = false;


        if (beginExamen == false){
            tvNumQuest.setVisibility(View.INVISIBLE);
            tvTime.setVisibility(View.INVISIBLE);
            tvIDQuest.setVisibility(View.INVISIBLE);
            btnIniciarExamen.setVisibility(View.VISIBLE);
            btnOpcionA.setVisibility(View.INVISIBLE);
            btnOpcionB.setVisibility(View.INVISIBLE);
            btnOpcionC.setVisibility(View.INVISIBLE);
            btnOpcionD.setVisibility(View.INVISIBLE);
            btnSendAnswer.setVisibility(View.INVISIBLE);
            imgBtnQuestion.setVisibility(View.INVISIBLE);
            getQuestions(questionCN_list,"CN");
            getQuestions(questionCS_list,"CS");
            getQuestions(questionMT_list,"MT");
            getQuestions(questionLC_list,"LC");
            getQuestions(questionIN_list,"IN");
        }

        btnIniciarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                question_All = new ArrayList<>();
                createExam(questionCN_list,questionMT_list,questionLC_list,questionCS_list,questionIN_list);
                printArray(question_All);
                counterQuestion = question_All.size() - 1;
                question = question_All.get(counterQuestion);

                tvIDQuest.setVisibility(View.VISIBLE);
                tvNumQuest.setVisibility(View.VISIBLE);
                tvTime.setVisibility(View.VISIBLE);
                btnIniciarExamen.setVisibility(View.GONE);
                btnOpcionA.setVisibility(View.VISIBLE);
                btnOpcionB.setVisibility(View.VISIBLE);
                btnOpcionC.setVisibility(View.VISIBLE);
                btnOpcionD.setVisibility(View.VISIBLE);
                btnSendAnswer.setVisibility(View.VISIBLE);
                imgBtnQuestion.setVisibility(View.VISIBLE);
                numPregunta++;

                updateUI(question);
         }
        });


        btnOpcionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionA.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.GRAY);
                btnOpcionC.setBackgroundColor(Color.GRAY);
                btnOpcionD.setBackgroundColor(Color.GRAY);
                selectedAnswer = 1;
                printArray(question_All);

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

            }
        });


        //  questionLista = createExam();
        btnSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedAnswer == 0 && counterQuestion >0) {
                    Snackbar snackbar = Snackbar.make(view.getRootView(),"Elige una respuesta ...",Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }else {
                    Question qrecived = question_All.get(counterQuestion);

                    if(isCorrect(selectedAnswer,qrecived)){
                        Snackbar snackbar = Snackbar.make(view.getRootView(),"No."+qrecived.getQuestionID()+" Respuesta Correcta",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        counterQuestion--;
                    }else{
                        Snackbar snackbar = Snackbar.make(view.getRootView(),"No."+qrecived.getQuestionID()+"Respuesta Incorrecta",Snackbar.LENGTH_LONG);
                        snackbar.show();
                        counterQuestion--;
                    }

                    if(counterQuestion<0){
                        btnSendAnswer.setText("Ver resultados ...");
                        toResults();
                    }else {
                        qrecived = question_All.get(counterQuestion);
                        numPregunta++;
                        updateUI(qrecived);
                    }
                }
            }
        });

        /*
        mDatabase = FirebaseDatabase.getInstance().getReference().child("QuestCompArea").child("CN");
        final String keyPreguntas = mDatabase.getKey();

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

        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100001.PNG?alt=media&token=760379d8-8a71-4116-962c-45744bcac038
        //https://firebasestorage.googleapis.com/v0/b/saber-go.appspot.com/o/Preguntas%2F100002.PNG?alt=media&token=7a95d633-e45c-45f5-a477-8c502ce5397f
        */




    }

    private boolean isCorrect(int selectedAnswer, Question qrecived) {

        if (selectedAnswer == 1 && qrecived.getAnswer().equals("A")) return true;
        else if(selectedAnswer == 2 && qrecived.getAnswer().equals("B")) return true;
        else if(selectedAnswer == 3 && qrecived.getAnswer().equals("C")) return  true;
        else if(selectedAnswer == 4 && qrecived.getAnswer().equals("D")) return  true;
        else{
            return false;
        }
     }

    public void getQuestions(final ArrayList<Question> arrayList, final String idArea){

        mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference refQuestArea = mDatabase.child("QuestCompArea").child(idArea);
        final DatabaseReference rfeQuest = mDatabase.child("Question");

        refQuestArea.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot recieve: dataSnapshot.getChildren())
                {
                    //String keyPregunta = recieve.getKey();
                    long value = (long) recieve.getValue();

                    rfeQuest.orderByChild("questionID").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists())
                            {

                                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                                {
                                    Question preguntaFounded = dataSnapshot1.getValue(Question.class);
                                    String info = String.valueOf(dataSnapshot.getValue());
                                    //pregunta.getQuestionID();
                                    arrayList.add(preguntaFounded);
                                    Log.e(TAG,"infos : "+preguntaFounded.getQuestionID()+" more info"+preguntaFounded.getQuestionID()+" : Pregunta agregada : "+idArea);
                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

    }

    private void createExam(ArrayList<Question> arrayCN, ArrayList<Question> arrayMT,ArrayList<Question> arrayLC, ArrayList<Question> arrayCS, ArrayList<Question> arrayIN)
    {
        random = new Random();

        int tamanoCN = 2; int tamanoLC = 2; int tamanoCS = 2; int tamanoIN = 2; int tamanoMT = 2;

        Collections.shuffle(arrayCN , random);
        Collections.shuffle(arrayMT , random);
        Collections.shuffle(arrayCS , random);
        Collections.shuffle(arrayLC , random);
        Collections.shuffle(arrayIN , random);

        for(int i =0; i<tamanoCN;i++){
            Question questionSend = arrayCN.get(i);
            question_All.add(questionSend);
        }
        for(int i =0; i<tamanoLC;i++){
            Question questionSend = arrayLC.get(i);
            question_All.add(questionSend);
        }
        for(int i =0; i<tamanoCS;i++){
            Question questionSend = arrayCS.get(i);
            question_All.add(questionSend);
        }
        for(int i =0; i<tamanoMT;i++){
            Question questionSend = arrayMT.get(i);
            question_All.add(questionSend);
        }
        for(int i =0; i<tamanoIN;i++){
            Question questionSend = arrayIN.get(i);
            question_All.add(questionSend);
        }

    }

    public void printArray(ArrayList<Question> array)
    {
      for (int x = 0; x<array.size(); x++)
      {
          Question questionNew = array.get(x);
          String info = questionNew.toString();
          long uID = questionNew.getQuestionID();
          Log.e(TAG,"Questionnumber : "+(x+1)+" ID : "+uID);
      }
    }
    private void updateUI(Question pregunta){
        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgBtnQuestion);
        Glide.with(this).load(pregunta.getQuestionURL()).into(imgBtnQuestion);
        photoViewAttacher.update();

        tvNumQuest.setText(numPregunta+"/"+String.valueOf(question_All.size()));
        tvIDQuest.setText(String.valueOf(pregunta.getQuestionID()));
        btnOpcionD.setBackgroundColor(Color.GRAY);
        btnOpcionB.setBackgroundColor(Color.GRAY);
        btnOpcionC.setBackgroundColor(Color.GRAY);
        btnOpcionA.setBackgroundColor(Color.GRAY);
        selectedAnswer = 0;

    }

    public void toResults()
    {
        Intent toResults = new Intent(this , ResultsExam.class);

        toResults.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toResults);
    }



}


















































    /*

    public void doExamDiagnostic(ArrayList<Question> questions, TextView tvprueba){

        String test = "";
        tvprueba.setText(test);


        for(int i=0; i<questions.size()-1; i++)
        {

            //int random = new Random().nextInt(questions.size())+1;
            Question question = questions.get(i);
            test = String.valueOf(question.getQuestionID());
            tvprueba.setText(test);
            String answer = String.valueOf(selectedAnswer);

            while (selectedAnswer!=0){

                if (question.getAnswer().equals(answer)){
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
                    selectedAnswer = 0;
                } else {
                    selectedAnswer = 0;
                }
            };
        }

    }

    private ArrayList<Question> createExam()
    {
        ArrayList<Question> questionCN;
        ArrayList<Question> questionCS;
        ArrayList<Question> questionLC;
        ArrayList<Question> questionMT;
        //ArrayList<Question> questionIN;
        ArrayList<Question> arrayList = new ArrayList<>();

        questionCN = getQuestions("CN", 11);


        //questionCS = getQuestions("CS", 10);
        //questionLC = getQuestions("LC", 5);
        //questionMT = getQuestions("MT", 4);
        //questionIN = getQuestions("IN", 10);

        arrayList.addAll(questionCN);
        //arrayList.addAll(questionCS);
        //arrayList.addAll(questionLC);
        //arrayList.addAll(questionMT);
       // arrayList.addAll(questionIN);


        return arrayList;

    }


    private ArrayList<Question> getQuestions(final String idArea, final int tamArray)
    {
        final ArrayList<Question> listTotal;
        listTotal = new ArrayList<Question>();
        ArrayList<Question> list = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
            DatabaseReference rfeQuestCompArea = mDatabase.child("QuestCompArea").child(idArea);
            final DatabaseReference rfeQuest = mDatabase.child("Question");

            rfeQuestCompArea.orderByKey().addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {

                    for (final DataSnapshot message: dataSnapshot.getChildren())
                    {
                        String keyPregunta = message.getKey();
                        long value = (long) message.getValue();
                        //DatabaseReference cases = mDatabase.child("Questions");
                        rfeQuest.orderByChild("questionID").equalTo(value).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {

                                    for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                                        Question preguntaFounded = dataSnapshot1.getValue(Question.class);
                                        String info = String.valueOf(dataSnapshot.getValue());
                                        //pregunta.getQuestionID();

                                        Log.e(TAG,"infoss : "+preguntaFounded.getAnswer()+" : Pregunta agregada : "+listTotal.size()+" Area :"+idArea);
                                        listTotal.add(preguntaFounded);
                                        Log.e(TAG, "TAM ListaTotal  : "+listTotal.size());
                                    }



                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        //String jum = message.child(keyPregunta).toString();

                        Log.e(TAG,"infos : "+keyPregunta+" : "+value);


                        //System.out.print(idPregunta);
                        //  pregunta = message.getValue(Question.class);


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });











       return listTotal;

    }

    private boolean correctAnswer(int seleccion) {

        if(seleccion == 1){
            return false;
        }else {
            return true;
        }

    }


}

*/