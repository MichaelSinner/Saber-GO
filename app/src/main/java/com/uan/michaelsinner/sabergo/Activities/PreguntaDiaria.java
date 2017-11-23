package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uan.michaelsinner.sabergo.Data.Question;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.text.Format;
import java.util.concurrent.TimeUnit;

import uk.co.senab.photoview.PhotoViewAttacher;

public class PreguntaDiaria extends AppCompatActivity {

    private RelativeLayout relativeLayout;
    private ImageButton imgBtnQuestion;
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
    private int counterQuestion = 1;
    private int answer = 0;


    private Question question;
    private User currentUser;

    String userString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_pregunta_diaria);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");

        relativeLayout = (RelativeLayout) findViewById(R.id.relativePregDiaria);
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

        btnSendAnswer = (Button) findViewById(R.id.btnSendAnswer);
        btnSendAnswer.setTypeface(font2);
        imgBtnQuestion = (ImageButton) findViewById(R.id.imgQuestion);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        userString = bundle.getString("USER");
        String questionString = bundle.getString("QUESTION");
        currentUser = new Gson().fromJson(userString, User.class);
        question = new Gson().fromJson(questionString, Question.class);

        tvNumQuest.setText("Saber Go");
        tvIDQuest.setText(question.getQuestionKey());

        showQuestion(question);


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

                    if (isCorrect(selectedAnswer, question)) {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Correcta", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        getPoints(question);
                        counterQuestion--;
                        answer = 1;

                    } else {
                        Snackbar snackbar = Snackbar.make(view.getRootView(), "No." + question.getQuestionID() + " Respuesta Incorrecta", Snackbar.LENGTH_LONG);
                        snackbar.show();
                        counterQuestion--;
                        answer = 2;

                    }

                    if (counterQuestion == 0) {

                        btnSendAnswer.setText(" Ver resultados ...");
                        counterQuestion--;
                    } else if(counterQuestion < 0){
                        toResults();
                    }
                }
            }
        });

        new CountDownTimer(30000 * 5, 1000) {

            public void onTick(long millisUntilFinished) {
                // tvTime.setText("seconds remaining: " + millisUntilFinished / 1000);

                tvTime.setText("" + String.format(FORMAT, TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
            }

            public void onFinish() {
                tvTime.setText("No extra Exp!");
            }
        }.start();


    }

    public void toResults(){

        Intent toResults = new Intent(PreguntaDiaria.this, ResultsPreg.class);
        //toResults.putExtra("USER", stringUser);
        //toResults.putExtra("ID", idUser);
        toResults.putExtra("USER", new Gson().toJson(currentUser));
        toResults.putExtra("right_MT", numRight_MT);
        toResults.putExtra("right_LC", numRight_LC);
        toResults.putExtra("right_CS", numRight_CS);
        toResults.putExtra("right_CN", numRight_CN);
        toResults.putExtra("right_IN", numRight_IN);
        toResults.putExtra("ANSWER",answer);
        toResults.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(toResults);




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
