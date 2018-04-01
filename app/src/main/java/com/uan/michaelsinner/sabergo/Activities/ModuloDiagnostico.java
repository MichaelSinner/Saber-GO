package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ModuloDiagnostico extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private ImageButton imgBtnQuestion;
    private Button btnOpcionA;
    private Button btnOpcionB;
    private Button btnOpcionC;
    private Button btnOpcionD;
    private Button btnSendAnswer;
    private Button btnIniciarExamen;
    private TextView tvTime;
    private TextView tvNumQuest;
    private TextView tvIDQuest;

    private Question question;

    private int counterTime = 0;
    private int selectedAnswer;
    private int numPregunta = 0;
    private int counterQuestion;

    private int numAnswerTrue = 0;
    private int numRight_LC = 0;
    private int numRight_MT = 0;
    private int numRight_CS = 0;
    private int numRight_CN = 0;
    private int numRight_IN = 0;

    private final int TOTAL_QUEST_CN = 5;
    private final int TOTAL_QUESTS = 5;
    private final int TOTAL_QUEST_LC = 1;
    private final int TOTAL_QUEST_CS = 1;
    private final int TOTAL_QUEST_MT = 1;
    private final int TOTAL_QUEST_IN = 1;

    private DatabaseReference mDatabase;
    private static final String TAG = "error";
    String idUser;
    String stringUser;
    private Thread t;

    private static final String FORMAT = "%02d:%02d:%02d";

    private ArrayList<Question> question_All;
    private ArrayList<Question> questionCN_list;
    private ArrayList<Question> questionMT_list;
    private ArrayList<Question> questionCS_list;
    private ArrayList<Question> questionIN_list;
    private ArrayList<Question> questionLC_list;

    private Random random;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_prueba_diagnostico);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");

        relativeLayout = (RelativeLayout) findViewById(R.id.relativeExmDiag);
        tvNumQuest = (TextView) findViewById(R.id.tvnumQuest);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvIDQuest = (TextView) findViewById(R.id.tvIdQuestion);
        btnOpcionA = (Button) findViewById(R.id.btnOpcionA);
        btnOpcionA.setTypeface(font2);
        btnOpcionB = (Button) findViewById(R.id.btnOpcionB);
        btnOpcionB.setTypeface(font2);
        btnOpcionC = (Button) findViewById(R.id.btnOpcionC);
        btnOpcionC.setTypeface(font2);
        btnOpcionD = (Button) findViewById(R.id.btnOpcionD);
        btnOpcionD.setTypeface(font2);
        btnIniciarExamen = (Button) findViewById(R.id.btnIniciarExamen);
        btnIniciarExamen.setTypeface(font2);
        btnSendAnswer = (Button) findViewById(R.id.btnSendAnswer);
        btnSendAnswer.setTypeface(font2);
        imgBtnQuestion = (ImageButton) findViewById(R.id.imgQuestion);

        questionCN_list = new ArrayList<>();
        questionCS_list = new ArrayList<>();
        questionMT_list = new ArrayList<>();
        questionLC_list = new ArrayList<>();
        questionIN_list = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            idUser = (String) bundle.get("ID");
            stringUser = bundle.getString("USER");
        }

        boolean beginExamen = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (beginExamen == false) {

            question_All = new ArrayList<>();

            tvNumQuest.setVisibility(View.INVISIBLE);
            tvTime.setVisibility(View.INVISIBLE);
            tvIDQuest.setVisibility(View.INVISIBLE);
            btnIniciarExamen.setVisibility(View.VISIBLE);
            btnIniciarExamen.setText("Cargando Preguntas ...");
            btnIniciarExamen.setBackgroundColor(Color.TRANSPARENT);
            btnIniciarExamen.setEnabled(false);
            btnOpcionA.setVisibility(View.INVISIBLE);
            btnOpcionB.setVisibility(View.INVISIBLE);
            btnOpcionC.setVisibility(View.INVISIBLE);
            btnOpcionD.setVisibility(View.INVISIBLE);
            btnSendAnswer.setVisibility(View.INVISIBLE);
            imgBtnQuestion.setVisibility(View.INVISIBLE);
            //     getQuestions(questionCN_list,"CN");
            //   getQuestions(questionCS_list,"CS");
            // getQuestions(questionMT_list,"MT");
            //getQuestions(questionLC_list,"LC");
            //getQuestions(questionIN_list,"IN");

            getQuestions(question_All);



        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnIniciarExamen.setEnabled(true);
                btnIniciarExamen.setBackgroundColor(Color.GREEN);
                btnIniciarExamen.setText("Iniciar Examen");
            }
        }, 8500);

        btnIniciarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (question_All.size() != 0) {
                    printArray(question_All);
                    //    createExam(questionCN_list,questionMT_list,questionLC_list,questionCS_list,questionIN_list);
                    //  getQuestions(question_All);
                    //printArray(question_All);
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


                    t = new Thread(){
                        @Override
                        public void run() {
                         while (!isInterrupted()){
                             try {
                                 Thread.sleep(1000);

                                 runOnUiThread(new Runnable() {
                                     @Override
                                     public void run() {
                                         counterTime++;
                                     }
                                 });



                             } catch (InterruptedException e) {
                                 e.printStackTrace();
                             }
                         }
                        }
                    };
                    t.start();

                    updateUI(question);
                } else {

                    Snackbar snackbar = Snackbar.make(view.getRootView(), "Espera a que carguen las preguntas ..." , Snackbar.LENGTH_LONG);
                    snackbar.show();


                }

            }
        });


        btnOpcionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionA.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.WHITE);
                btnOpcionC.setBackgroundColor(Color.WHITE);
                btnOpcionD.setBackgroundColor(Color.WHITE);
                selectedAnswer = 1;
                //printArray(question_All);

            }
        });

        btnOpcionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionB.setBackgroundColor(Color.GREEN);
                btnOpcionA.setBackgroundColor(Color.WHITE);
                btnOpcionC.setBackgroundColor(Color.WHITE);
                btnOpcionD.setBackgroundColor(Color.WHITE);
                selectedAnswer = 2;

            }
        });

        btnOpcionC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionC.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.WHITE);
                btnOpcionA.setBackgroundColor(Color.WHITE);
                btnOpcionD.setBackgroundColor(Color.WHITE);
                selectedAnswer = 3;

            }
        });

        btnOpcionD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnOpcionD.setBackgroundColor(Color.GREEN);
                btnOpcionB.setBackgroundColor(Color.WHITE);
                btnOpcionC.setBackgroundColor(Color.WHITE);
                btnOpcionA.setBackgroundColor(Color.WHITE);
                selectedAnswer = 4;

            }
        });


        //  questionLista = createExam();
        btnSendAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selectedAnswer == 0 && counterQuestion > 0) {
                    Snackbar snackbar = Snackbar.make(view.getRootView(), " Elige una respuesta ...", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {

                    question = question_All.get(counterQuestion);

                    if (isCorrect(selectedAnswer, question)) {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Correcta", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        getPoints(question);
                        counterQuestion--;
                    } else {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Incorrecta", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        counterQuestion--;
                    }

                    if (counterQuestion < 0) {
                        Log.e(TAG, "Numero de rtas correctas" + numAnswerTrue + " LC :" + numRight_LC + " MT :" + numRight_MT + " CS :" + numRight_CS + " CN :" + numRight_CN + " IN :" + numRight_IN);
                        btnSendAnswer.setBackgroundColor(getResources().getColor(R.color.colorContinue));
                        btnSendAnswer.setText("Ver resultados ...");

                        toResults();

                    } else {
                        question = question_All.get(counterQuestion);
                        numPregunta++;
                        updateUI(question);
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

        new CountDownTimer(30000 * 10, 1000) {

            public void onTick(long millisUntilFinished) {
                // tvTime.setText("seconds remaining: " + millisUntilFinished / 1000);

                tvTime.setText("" + String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(                              TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));

            }

            public void onFinish() {
                tvTime.setText("done!");
            }
        }.start();


    }

    private boolean isCorrect(int selectedAnswer, Question qrecived) {

        if (selectedAnswer == 1 && qrecived.getAnswer().equals("A")) return true;
        else if (selectedAnswer == 2 && qrecived.getAnswer().equals("B")) return true;
        else if (selectedAnswer == 3 && qrecived.getAnswer().equals("C")) return true;
        else if (selectedAnswer == 4 && qrecived.getAnswer().equals("D")) return true;
        else {
            return false;
        }
    }

    public void getQuestions(final ArrayList<Question> arrayList) {
        random = new Random();
        //int[] arrayList1 = generadorAleatorios(tamano);
        String[] listaAreas = {"MT", "LC", "CS", "CN", "IN"};
        String[] listaAreasNombre = {"Matemáticas", "Lectura crítica", "Ciencias Sociales", "Ciencias Naturales", "Inglés"};

        DatabaseReference refQuestArea = mDatabase.child("QuestCompArea");
        //DatabaseReference refSizeArray = refQuestArea.child("_AllQuest");
        DatabaseReference refQuestions = mDatabase.child("Question");

        int countArray = listaAreas.length - 1;

        do {

            for(int i = 0; i<=5;i++)
            {
                refQuestions.orderByChild("area").equalTo(listaAreas[countArray]).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int questionCount = (int) dataSnapshot.getChildrenCount();
                        int rand = random.nextInt(questionCount);
                        Iterator itr = dataSnapshot.getChildren().iterator();
                        Log.e(TAG, " "+questionCount+"   "+rand);

                        for(int i = 0; i < rand; i++) {
                            itr.next();
                        }
                        DataSnapshot childSnapshot = (DataSnapshot) itr.next();
                        Question questionAdd = childSnapshot.getValue(Question.class);
                        questionAdd.setQuestionKey(childSnapshot.getKey());

                        Log.e(TAG, questionAdd.toString());

                        arrayList.add(questionAdd);


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


            Toast.makeText(this, "Cargando preguntas de " + listaAreasNombre[countArray], Toast.LENGTH_SHORT).show();
            countArray--;

        } while (countArray >= 0);
    }

    public int[] generadorAleatorios(int tam) {
        int resultado;
        int aux = tam;
        int[] numsRandom = new int[tam];
        int[] numbers = new int[tam];

        for (int i = 0; i < tam; i++) numbers[i] = i + 1;

        for (int i = 0; i < tam; i++) {
            resultado = random.nextInt(aux);
            numbers[resultado] = numbers[aux - 1];
            aux--;
        }

        return numsRandom;
    }

    private void createExam(ArrayList<Question> arrayCN, ArrayList<Question> arrayMT, ArrayList<Question> arrayLC, ArrayList<Question> arrayCS, ArrayList<Question> arrayIN) {
        random = new Random();

        int tamanoCN = 2;
        int tamanoLC = 2;
        int tamanoCS = 2;
        int tamanoIN = 2;
        int tamanoMT = 2;

        Collections.shuffle(arrayCN, random);
        Collections.shuffle(arrayMT, random);
        Collections.shuffle(arrayCS, random);
        Collections.shuffle(arrayLC, random);
        Collections.shuffle(arrayIN, random);

        for (int i = 0; i < TOTAL_QUEST_CN; i++) {
            Question questionSend = arrayCN.get(i);
            question_All.add(questionSend);
        }

        for (int i = 0; i < TOTAL_QUEST_LC; i++) {
            Question questionSend = arrayLC.get(i);
            question_All.add(questionSend);
        }

        for (int i = 0; i < TOTAL_QUEST_CS; i++) {
            Question questionSend = arrayCS.get(i);
            question_All.add(questionSend);
        }

        for (int i = 0; i < TOTAL_QUEST_MT; i++) {
            Question questionSend = arrayMT.get(i);
            question_All.add(questionSend);
        }

        for (int i = 0; i < TOTAL_QUEST_IN; i++) {
            Question questionSend = arrayIN.get(i);
            question_All.add(questionSend);
        }

    }

    public void printArray(ArrayList<Question> array) {
        for (int x = 0; x < array.size(); x++) {
            Question questionNew = array.get(x);
            String info = questionNew.toString();
            long uID = questionNew.getQuestionID();
            Log.e(TAG, "Questionnumber : " + (x + 1) + " ID : " + uID);
        }
    }

    private void updateUI(Question pregunta) {

        //gs://saber-go.appspot.com/Questions/CN0004.png
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://saber-go.appspot.com/Questions/" + pregunta.getQuestionKey() + ".png");

        PhotoViewAttacher attacher = new PhotoViewAttacher(imgBtnQuestion);
        Glide.with(this).using(new FirebaseImageLoader()).load(gsReference).into(imgBtnQuestion);
        attacher.update();
        /*
        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("Questions/"+pregunta.getQuestionCompetencia()+".png");

        PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imgBtnQuestion);
        Glide.with(this).using(new FirebaseImageLoader()).load(reference).into(imgBtnQuestion);
        photoViewAttacher.update();
        */

        tvNumQuest.setText(numPregunta + "/" + String.valueOf(question_All.size()));
        tvIDQuest.setText(String.valueOf(pregunta.getQuestionKey()));
        btnOpcionD.setBackgroundColor(Color.WHITE);
        btnOpcionB.setBackgroundColor(Color.WHITE);
        btnOpcionC.setBackgroundColor(Color.WHITE);
        btnOpcionA.setBackgroundColor(Color.WHITE);
        selectedAnswer = 0;

    }

    public void toResults() {
        Intent toResults = new Intent(ModuloDiagnostico.this, ResultsExam.class);
        toResults.putExtra("USER", stringUser);
        toResults.putExtra("ID", idUser);
        toResults.putExtra("right_MT", numRight_MT);
        toResults.putExtra("right_LC", numRight_LC);
        toResults.putExtra("right_CS", numRight_CS);
        toResults.putExtra("right_CN", numRight_CN);
        toResults.putExtra("right_IN", numRight_IN);
        toResults.putExtra("Seconds", counterTime);
        toResults.putExtra("right_quests", numAnswerTrue);


        startActivity(toResults);
        this.finish();


    }

    public void getPoints(Question question) {

        if (question.getArea().equals("MT")) {
            numRight_MT++;
        }

        if (question.getArea().equals("LC")) {
            numRight_LC++;
        }

        if (question.getArea().equals("CS")) {
            numRight_CS++;
        }

        if (question.getArea().equals("CN")) {
            numRight_CN++;
        }

        if (question.getArea().equals("IN")) {
            numRight_IN++;
        }

        numAnswerTrue++;
    }


}
