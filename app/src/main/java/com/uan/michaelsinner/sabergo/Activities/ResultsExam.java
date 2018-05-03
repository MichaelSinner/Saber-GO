package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ResultsExam extends AppCompatActivity {

    private Button btnVolver;
    private static final String TAG = "info";

    private TextView tvNumRightAnswers, tvNumWrongAnswers, tvNumMT, tvNumCS, tvNumCN, tvNumIN, tvNumLC,
            tvFragMT, tvFragCS, tvFragCN, tvFragIN, tvFragLC, tvTime, tvExp, tvMoney;
    private TextView tvPrueba;
    private TextView tvTitle, tvEarn;
    private TextView tvRes01, tvRes02, tvRes03, tvRes04, tvRes05, tvRes06, tvRes07, tvRes08, tvRes09, tvRes10, tvRes11, tvRes12, tvRes13, tvRes14, tvRes15;
    private ProgressBar progressBar;
    private ImageView imageScore;

    private final int TOTAL_QUESTIONS = 30;
    private int rightAnswer;
    private int wrongAnswer;
    private int questMT;
    private int questCS;
    private int questCN;
    private int questLC;
    private int questIN;
    private int money;
    private int experience;
    private int time;

    long numExams;
    long numQuestionsMT;
    long numQuestionsLC;
    long numQuestionsCS;
    long numQuestionsCN;
    long numQuestionsIN;
    long numFragMT;
    long numFragLC;
    long numFragCS;
    long numFragCN;
    long numFragIN;
    long moneySend;
    long exp;

    String userID;
    String stringUser;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");

    private final String PATH_STUDENTS = "https://saber-go.firebaseio.com/Student";

    private User currentUser;
    FirebaseAuth.AuthStateListener fireAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        setContentView(R.layout.activity_results_exam);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");



        //reference.addChildEventListener(new UserChildEventListener());

        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser userf = firebaseAuth.getCurrentUser();

                if (userf != null && currentUser != null) {
                    Log.e(TAG, "cambiosito");

                } else {
                    toMainMenu();
                }
            }
        };

        progressBar = (ProgressBar) findViewById(R.id.pbRightWrong);
        imageScore = (ImageView) findViewById(R.id.imgResultExam);
        tvNumRightAnswers = (TextView) findViewById(R.id.tvResRightAnsw);
        tvNumRightAnswers.setTypeface(font2);
        tvNumWrongAnswers = (TextView) findViewById(R.id.tvResWrongAnsw);
        tvNumWrongAnswers.setTypeface(font2);

        tvNumMT = (TextView) findViewById(R.id.tvResMT);
        tvNumMT.setTypeface(font2);
        tvNumCN = (TextView) findViewById(R.id.tvResCN);
        tvNumCN.setTypeface(font2);
        tvNumCS = (TextView) findViewById(R.id.tvResCS);
        tvNumCS.setTypeface(font2);
        tvNumIN = (TextView) findViewById(R.id.tvResIN);
        tvNumIN.setTypeface(font2);
        tvNumLC = (TextView) findViewById(R.id.tvResLC);
        tvNumLC.setTypeface(font2);


        tvFragMT = (TextView) findViewById(R.id.tvResFragMT);
        tvFragMT.setTypeface(font2);
        tvFragCN = (TextView) findViewById(R.id.tvResFragCN);
        tvFragCN.setTypeface(font2);
        tvFragCS = (TextView) findViewById(R.id.tvResFragCS);
        tvFragCS.setTypeface(font2);
        tvFragIN = (TextView) findViewById(R.id.tvResFragIN);
        tvFragIN.setTypeface(font2);
        tvFragLC = (TextView) findViewById(R.id.tvResFragLC);
        tvFragLC.setTypeface(font2);

        tvRes01 = (TextView) findViewById(R.id.tvRes01);
        tvRes01.setTypeface(font2);
        tvRes02 = (TextView) findViewById(R.id.tvRes02);
        tvRes02.setTypeface(font2);
        tvRes03 = (TextView) findViewById(R.id.tvRes03);
        tvRes03.setTypeface(font2);
        tvRes04 = (TextView) findViewById(R.id.tvRes04);
        tvRes04.setTypeface(font2);
        tvRes05 = (TextView) findViewById(R.id.tvRes05);
        tvRes05.setTypeface(font2);
        tvRes06 = (TextView) findViewById(R.id.tvRes06);
        tvRes06.setTypeface(font2);
        tvRes07 = (TextView) findViewById(R.id.tvRes07);
        tvRes07.setTypeface(font2);
        tvRes08 = (TextView) findViewById(R.id.tvRes08);
        tvRes08.setTypeface(font2);
        tvRes09 = (TextView) findViewById(R.id.tvRes09);
        tvRes09.setTypeface(font2);
        tvRes11 = (TextView) findViewById(R.id.tvRes11);
        tvRes11.setTypeface(font2);
        tvRes12 = (TextView) findViewById(R.id.tvRes12);
        tvRes12.setTypeface(font2);
        tvRes13 = (TextView) findViewById(R.id.tvRes13);
        tvRes13.setTypeface(font2);
        tvRes14 = (TextView) findViewById(R.id.tvRes14);
        tvRes14.setTypeface(font2);
        tvRes15 = (TextView) findViewById(R.id.tvRes15);
        tvRes15.setTypeface(font2);
        tvRes10 = (TextView) findViewById(R.id.tvRes10);
        tvRes10.setTypeface(font2);


        tvTime = (TextView) findViewById(R.id.tvResTime);
        tvTime.setTypeface(font2);
        tvMoney = (TextView) findViewById(R.id.tvResMoney);
        tvMoney.setTypeface(font2);
        tvExp = (TextView) findViewById(R.id.tvResExp);
        tvExp.setTypeface(font2);

        tvTitle = (TextView) findViewById(R.id.tvTitleRPD);
        tvTitle.setTypeface(font2);

        tvEarn = (TextView) findViewById(R.id.tvRecompensasPD);
        tvEarn.setTypeface(font2);
        //tvPrueba = (TextView) findViewById(R.id.tvSagrado);

        Bundle bundle = getIntent().getExtras();

        String userSrting = bundle.getString("USER");
        currentUser = new Gson().fromJson(userSrting, User.class);

        rightAnswer = (int) bundle.get("right_quests");
        wrongAnswer = TOTAL_QUESTIONS - rightAnswer;
        questMT = (int) bundle.get("right_MT");
        questLC = (int) bundle.get("right_LC");
        questCS = (int) bundle.get("right_CS");
        questCN = (int) bundle.get("right_CN");
        questIN = (int) bundle.get("right_IN");
        time = (int) bundle.get("Seconds");
        money = rightAnswer * 500;
        experience = rightAnswer + 10;
        Log.e(TAG, "Seconds: "+time);



        //stringUser = bundle.getString("USER");

        //currentUser = new Gson().fromJson(stringUser, User.class);
        //Log.e(TAG,"Usuario recuperado"+currentUser.toString());

        userID = currentUser.getUserID();


        btnVolver = (Button) findViewById(R.id.btnVolverMenu);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (update(currentUser)) {
                    toMainMenu();
                }

            }
        });
        btnVolver.setTypeface(font);


    }


    public boolean update(final User user) {

        //loadUI();

        currentUser.setNumExamDiagnostic(currentUser.getNumExamDiagnostic() + 1);
        currentUser.setNumPreguntasMT(currentUser.getNumPreguntasMT() + questMT);
        currentUser.setNumPreguntasLC(currentUser.getNumPreguntasLC() + questLC);
        currentUser.setNumPreguntasCS(currentUser.getNumPreguntasCS() + questCS);
        currentUser.setNumPreguntasCN(currentUser.getNumPreguntasCN() + questCN);
        currentUser.setNumPreguntasIN(currentUser.getNumPreguntasIN() + questIN);
        currentUser.setPuntosMT(currentUser.getPuntosMT() + questMT);
        currentUser.setPuntosLC(currentUser.getPuntosLC() + questLC);
        currentUser.setPuntosCS(currentUser.getPuntosCS() + questCS);
        currentUser.setPuntosCN(currentUser.getPuntosCN() + questCN);
        currentUser.setPuntosIN(currentUser.getPuntosIN() + questIN);
        currentUser.setUserDinero(currentUser.getUserDinero() + money);
        currentUser.setProgressLevelExp(currentUser.getProgressLevelExp() + experience);
        currentUser.setDoExamDiagnostic(true);


        //allInfo = getIntent().getExtras();
        //stringUser = allInfo.getString("USER");
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Student").child(user.getUserID());
        //User userUpdated =new Gson().fromJson(stringUser, User.class);
        //int numAcum = user.getNumExamDiagnostic();
        //numAcum++;
        //user.setNumExamDiagnostic(numAcum);
        df.setValue(user);

        // tvPruebaUpdate.setText(user.getNumExamDiagnostic());


        Toast.makeText(getApplicationContext(), "Información del usuario actualizada", Toast.LENGTH_SHORT).show();
        return true;


    }


    public void toMainMenu() {
/*
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this,intent);
        this.finish();
*/
        Intent toMainMenu = new Intent(ResultsExam.this, MainMenu.class);

        toMainMenu.putExtra("USER", new Gson().toJson(currentUser));
        toMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(toMainMenu);
        this.finish();


    }



    public void loadUI() {
        if (rightAnswer >= 0 && rightAnswer < 4)
            imageScore.setImageResource(R.drawable.nota_7);
        else if (rightAnswer >= 5 && rightAnswer <= 8)
            imageScore.setImageResource(R.drawable.nota_6);
        else if (rightAnswer >= 9 && rightAnswer <= 12)
            imageScore.setImageResource(R.drawable.nota_5);
        else if (rightAnswer >= 13 && rightAnswer <= 16)
            imageScore.setImageResource(R.drawable.nota_4);
        else if (rightAnswer >= 17 && rightAnswer <= 20)
            imageScore.setImageResource(R.drawable.nota_3);
        else if (rightAnswer >= 21 && rightAnswer <= 24)
            imageScore.setImageResource(R.drawable.nota_2);
        else if (rightAnswer >= 25 && rightAnswer <= 30)
            imageScore.setImageResource(R.drawable.nota_1);

        progressBar.setProgress(rightAnswer);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        progressBar.setMax(30);
        tvNumRightAnswers.setText(String.valueOf(rightAnswer));
        tvNumWrongAnswers.setText(String.valueOf(wrongAnswer));
        tvNumMT.setText(String.valueOf(questMT));
        tvNumCS.setText(String.valueOf(questCS));
        tvNumCN.setText(String.valueOf(questCN));
        tvNumIN.setText(String.valueOf(questIN));
        tvNumLC.setText(String.valueOf(questLC));
        tvFragMT.setText("+" + String.valueOf(questMT));
        tvFragCS.setText("+" + String.valueOf(questCS));
        tvFragCN.setText("+" + String.valueOf(questCN));
        tvFragIN.setText("+" + String.valueOf(questIN));
        tvFragLC.setText("+" + String.valueOf(questLC));
        tvExp.setText("+" + String.valueOf(experience));
        tvMoney.setText("+" + String.valueOf(money));

        if (time>59){
            int secs = time % 60;
            int minutues = time / 60;
            tvTime.setText(minutues+"'"+secs+"''");
        }else{
            tvTime.setText("0'"+time+"''");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        //tvPrueba = (TextView) findViewById(R.id.tvSagrado);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = mDatabase.child("Student");

        loadUI();


        userReference.orderByKey().equalTo(userID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    currentUser = message.getValue(User.class);

                   // tvPrueba.setText(String.valueOf(currentUser.getNumExamDiagnostic()));
                    Log.e(TAG, "Estudiante Capturado de fire : " + currentUser.toString());
                    return;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    class UserChildEventListener implements com.google.firebase.database.ChildEventListener {

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            User nuewUer = dataSnapshot.getValue(User.class);

            if (currentUser.getUserID().equals(key)) {
                currentUser.setValues(nuewUer);
            }
        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

}
