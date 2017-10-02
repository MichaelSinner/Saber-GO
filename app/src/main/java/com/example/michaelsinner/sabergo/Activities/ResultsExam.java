package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.Data.User;
import com.example.michaelsinner.sabergo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class ResultsExam extends AppCompatActivity
{

    private Button btnVolver;
    private static final String TAG ="info";

    private TextView tvNumRightAnswers, tvNumWrongAnswers, tvNumMT, tvNumCS, tvNumCN, tvNumIN, tvNumLC,
            tvFragMT, tvFragCS, tvFragCN, tvFragIN, tvFragLC, tvTime, tvExp, tvMoney;
    private TextView tvPrueba;
    private ProgressBar progressBar;
    private ImageView imageScore;


    private final int TOTAL_QUESTIONS = 5;
    private int rightAnswer;
    private int wrongAnswer;
    private int questMT;
    private int questCS;
    private int questCN;
    private int questLC;
    private int questIN;
    private int money;
    private int experience;

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

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");;
    private final String PATH_STUDENTS = "https://saber-go.firebaseio.com/Student";

    private User currentUser;
    FirebaseAuth.AuthStateListener fireAuthStateListener;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results_exam);

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
        tvNumWrongAnswers = (TextView) findViewById(R.id.tvResWrongAnsw);

        tvNumMT = (TextView) findViewById(R.id.tvResMT);
        tvNumCN = (TextView) findViewById(R.id.tvResCN);
        tvNumCS = (TextView) findViewById(R.id.tvResCS);
        tvNumIN = (TextView) findViewById(R.id.tvResIN);
        tvNumLC = (TextView) findViewById(R.id.tvResLC);


        tvFragMT = (TextView) findViewById(R.id.tvResFragMT);
        tvFragCN = (TextView) findViewById(R.id.tvResFragCN);
        tvFragCS = (TextView) findViewById(R.id.tvResFragCS);
        tvFragIN = (TextView) findViewById(R.id.tvResFragIN);
        tvFragLC = (TextView) findViewById(R.id.tvResFragLC);

        tvTime = (TextView) findViewById(R.id.tvResTime);
        tvMoney = (TextView) findViewById(R.id.tvResMoney);
        tvExp = (TextView) findViewById(R.id.tvResExp);
        tvPrueba = (TextView) findViewById(R.id.tvSagrado);

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
        money = rightAnswer * 500;
        experience = rightAnswer + 10;

        //stringUser = bundle.getString("USER");

            //currentUser = new Gson().fromJson(stringUser, User.class);
            //Log.e(TAG,"Usuario recuperado"+currentUser.toString());

        userID = currentUser.getUserID();



        btnVolver = (Button) findViewById(R.id.btnVolverMenu);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (update(currentUser)){
                    toMainMenu();
                }

            }
        });



    }



    public boolean update(final User user){

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


        Toast.makeText(getApplicationContext(),"Información del usuario actualizada",Toast.LENGTH_SHORT).show();
        return true;




    }





    public void toMainMenu()
    {
/*
        Intent intent = NavUtils.getParentActivityIntent(this);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        NavUtils.navigateUpTo(this,intent);
        this.finish();
*/
        Intent toMainMenu = new Intent(ResultsExam.this , MainMenu.class);

        toMainMenu.putExtra("USER",new Gson().toJson(currentUser));
        toMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP );
        startActivity(toMainMenu);
        this.finish();


    }
    public void saveInfo(){



        //HashMap<String,Object> hashMap = currentUser.toMap();

        update(currentUser);







    }


    public void loadUI(){
        if (rightAnswer >= 0 && rightAnswer<10)
            imageScore.setImageResource(R.drawable.nota_7);
        else if(rightAnswer >= 10 && rightAnswer <= 19)
            imageScore.setImageResource(R.drawable.nota_6);
        else if(rightAnswer >= 20 && rightAnswer <= 29 )
            imageScore.setImageResource(R.drawable.nota_5);
        else if(rightAnswer >= 30 && rightAnswer <= 34)
            imageScore.setImageResource(R.drawable.nota_4);
        else if(rightAnswer >= 35 && rightAnswer <= 39)
            imageScore.setImageResource(R.drawable.nota_3);
        else if(rightAnswer >= 40 && rightAnswer <= 44)
            imageScore.setImageResource(R.drawable.nota_2);
        else if(rightAnswer >= 45 && rightAnswer <= 50)
            imageScore.setImageResource(R.drawable.nota_1);

        progressBar.setProgress(rightAnswer);
        progressBar.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        progressBar.setMax(25);
        tvNumRightAnswers.setText(String.valueOf(rightAnswer));
        tvNumWrongAnswers.setText(String.valueOf(wrongAnswer));
        tvNumMT.setText(String.valueOf(questMT));
        tvNumCS.setText(String.valueOf(questCS));
        tvNumCN.setText(String.valueOf(questCN));
        tvNumIN.setText(String.valueOf(questIN));
        tvNumLC.setText(String.valueOf(questLC));
        tvFragMT.setText("+"+String.valueOf(questMT));
        tvFragCS.setText("+"+String.valueOf(questCS));
        tvFragCN.setText("+"+String.valueOf(questCN));
        tvFragIN.setText("+"+String.valueOf(questIN));
        tvFragLC.setText("+"+String.valueOf(questLC));
        tvExp.setText("+"+String.valueOf(experience));
        tvMoney.setText("+"+String.valueOf(money));
    }

    @Override
    protected void onStart() {
        super.onStart();

        tvPrueba = (TextView) findViewById(R.id.tvSagrado);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = mDatabase.child("Student");

        loadUI();


            userReference.orderByKey().equalTo(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot message : dataSnapshot.getChildren()) {
                            currentUser = message.getValue(User.class);

                            tvPrueba.setText(String.valueOf(currentUser.getNumExamDiagnostic()));
                            Log.e(TAG, "Estudiante Capturado de fire : " + currentUser.toString());
                            return;
                        }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
    }





    class UserChildEventListener implements com.google.firebase.database.ChildEventListener{

        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            String key = dataSnapshot.getKey();
            User nuewUer = dataSnapshot.getValue(User.class);

            if(currentUser.getUserID().equals(key)){
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
