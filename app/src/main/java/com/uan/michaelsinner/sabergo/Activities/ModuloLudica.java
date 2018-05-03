package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;

import java.util.Iterator;
import java.util.Random;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ModuloLudica extends AppCompatActivity {
    private static final String TAG = "1";
    private ImageButton imgBtnQuestion;
    private Button btnIniciarExamen;
    private Button btnOpcionA;
    private Button btnOpcionB;
    private Button btnOpcionC;
    private Button btnOpcionD;
    private Button btnSendAnswer;

    private static final String FORMAT = "%02d:%02d:%02d";
    private int numRight_LC = 0;
    private int numRight_MT = 0;
    private int numRight_CS = 0;
    private int numRight_CN = 0;
    private int numRight_IN = 0;


    private TextView tvTime;
    private TextView tvNumQuest;
    private TextView tvIDQuest;

    private int selectedAnswer = 0;
    private int answer = 0;


    private Question question;
    private User currentUser;

    String userString;
    private Thread t;

    private Random random;
    private Handler handler = new Handler();

    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_modulo_ar);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");


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

        //-- Colocar pregunta --//

        tvNumQuest.setText("Saber Go");




        boolean beginExamen = false;
        mDatabase = FirebaseDatabase.getInstance().getReference();


        if (beginExamen == false) {



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

            getQuestion();




        }

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                btnIniciarExamen.setEnabled(true);
                btnIniciarExamen.setBackgroundColor(Color.GREEN);
                btnIniciarExamen.setText("Iniciar Examen");
            }
        }, 5000);

        btnIniciarExamen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (question != null) {

                    //    createExam(questionCN_list,questionMT_list,questionLC_list,questionCS_list,questionIN_list);
                    //  getQuestions(question_All);
                    //printArray(question_All);
                    tvIDQuest.setText(question.getQuestionKey());

                    tvIDQuest.setVisibility(View.VISIBLE);
                    tvNumQuest.setVisibility(View.VISIBLE);
                    tvTime.setVisibility(View.VISIBLE);
                    tvTime.setText("Meteoritos");
                    btnIniciarExamen.setVisibility(View.GONE);
                    btnOpcionA.setVisibility(View.VISIBLE);
                    btnOpcionB.setVisibility(View.VISIBLE);
                    btnOpcionC.setVisibility(View.VISIBLE);
                    btnOpcionD.setVisibility(View.VISIBLE);
                    btnSendAnswer.setVisibility(View.VISIBLE);
                    imgBtnQuestion.setVisibility(View.VISIBLE);





                    showQuestion(question);

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

                if (selectedAnswer == 0) {
                    Snackbar snackbar = Snackbar.make(view.getRootView(), " Elige una respuesta ...", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                } else {

                    if (isCorrect(selectedAnswer, question)) {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Correcta", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        getPoints(question);
                        answer = 1;
                        toResults();

                    } else {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Incorrecta", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        answer = 2;
                        toResults();
                    }
                }
            }
        });




    }

    public void getQuestion(){
        random = new Random();

        DatabaseReference refQuestions = mDatabase.child("Question");

        refQuestions.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                int questionCount = (int) dataSnapshot.getChildrenCount();
                //int questionCount = 155;
                int rand = random.nextInt(questionCount);
                Iterator itr = dataSnapshot.getChildren().iterator();
                Log.e(TAG, " "+questionCount+"   "+rand);

                for(int i = 0; i < rand; i++) {
                    itr.next();
                }
                DataSnapshot childSnapshot = (DataSnapshot) itr.next();
                question = childSnapshot.getValue(Question.class);
                question.setQuestionKey(childSnapshot.getKey());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void toResults(){
        FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
        final String ID = userF.getUid();


        Intent toResults = new Intent(this, ResultsPregLud.class);
        //toResults.putExtra("USER", stringUser);
        //toResults.putExtra("ID", idUser);
        //toResults.putExtra("USER", new Gson().toJson(currentUser));
        toResults.putExtra("right_MT", numRight_MT);
        toResults.putExtra("right_LC", numRight_LC);
        toResults.putExtra("right_CS", numRight_CS);
        toResults.putExtra("right_CN", numRight_CN);
        toResults.putExtra("right_IN", numRight_IN);
        toResults.putExtra("ANSWER",answer);
        toResults.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(toResults);
        this.finish();

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

    public void showQuestion(Question pregunta){

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

        tvNumQuest.setText("Saber GO");
        tvIDQuest.setText(String.valueOf(pregunta.getQuestionKey()));
        btnOpcionD.setBackgroundColor(Color.WHITE);
        btnOpcionB.setBackgroundColor(Color.WHITE);
        btnOpcionC.setBackgroundColor(Color.WHITE);
        btnOpcionA.setBackgroundColor(Color.WHITE);
        selectedAnswer = 0;

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


    }

}
