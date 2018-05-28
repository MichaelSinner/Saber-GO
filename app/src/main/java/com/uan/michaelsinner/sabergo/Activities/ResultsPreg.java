package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
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

public class ResultsPreg extends AppCompatActivity {

    private Button btnVolver;
    private User currentUser;
    private String userID;
    private ImageButton imgResultPreg;

    private TextView tvAnser, tvFrag, tvExp, tvRecompensa,tvTitle,tv02,tv01;

    private int questMT;
    private int questCS;
    private int questCN;
    private int questLC;
    private int questIN;
    private int money;
    private int experience;
    private int ANSWER;
    private String ANSWERQ;

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Student");
    private FirebaseAuth fireAuthStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);
        setContentView(R.layout.activity_results_preg);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");

        imgResultPreg = (ImageButton) findViewById(R.id.imgResultPreg);
        tvAnser = (TextView)findViewById(R.id.tvResWrongAnsw);
        tvAnser.setTypeface(font);
        tvExp = (TextView) findViewById(R.id.tvResExp);
        tvExp.setTypeface(font);
        tvFrag = (TextView) findViewById(R.id.tvResFrag);
        tvFrag.setTypeface(font);
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvTitle.setTypeface(font);
        tv01 = (TextView)findViewById(R.id.tvRes03);
        tv01.setTypeface(font);
        tv02 = (TextView)findViewById(R.id.tvRes04);
        tv02.setTypeface(font);

        tvRecompensa = (TextView) findViewById(R.id.tvRecompensas);
        tvRecompensa.setTypeface(font);

        Bundle bundle = getIntent().getExtras();

        String userSrting = bundle.getString("USER");
        currentUser = new Gson().fromJson(userSrting, User.class);

        questMT = (int) bundle.get("right_MT");
        questLC = (int) bundle.get("right_LC");
        questCS = (int) bundle.get("right_CS");
        questCN = (int) bundle.get("right_CN");
        questIN = (int) bundle.get("right_IN");
        ANSWER = (int) bundle.get("ANSWER");
        ANSWERQ = (String) bundle.get("ANSWERQ");
        money = 10;
        experience = 1;
        userID = currentUser.getUserID();

        showAnswersImage(ANSWER);


        btnVolver = (Button) findViewById(R.id.btnVolverMenuPD);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (update(currentUser)) {
                    toMainMenu();
                }

            }
        });
        btnVolver.setTypeface(font2);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //tvPrueba = (TextView) findViewById(R.id.tvSagrado);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userReference = mDatabase.child("Student");

        FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();

        if(userF!=null)
        {
            userID = userF.getUid();
            userReference.orderByKey().equalTo(userID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    for (DataSnapshot message : dataSnapshot.getChildren()) {
                        currentUser = message.getValue(User.class);


                        return;
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }





    public void toMainMenu() {

        Intent toMainMenu = new Intent(ResultsPreg.this, MainMenu.class);
        toMainMenu.putExtra("USER", new Gson().toJson(currentUser));
        toMainMenu.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(toMainMenu);
        this.finish();
    }

    public boolean update(final User user) {

        //loadUI();

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

    public void showAnswersImage(final int Answer){

        if (Answer == 1){
            imgResultPreg.setImageResource(R.drawable.ic_victory);
            tvAnser.setText(ANSWERQ);
        } else if (Answer == 2){
            imgResultPreg.setImageResource(R.drawable.ic_defeat);
            tvAnser.setText(ANSWERQ);
            tvExp.setText("+0");
            tvFrag.setText("+0");
        }



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
