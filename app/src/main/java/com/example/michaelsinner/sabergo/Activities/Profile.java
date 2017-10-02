package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.michaelsinner.sabergo.Data.User;
import com.example.michaelsinner.sabergo.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Profile extends AppCompatActivity
{
    private ProgressBar pgbExperience, pgbMT, pgbCN, pgbCS, pgbLC, pgbIN;
    private  DatabaseReference mDatabase;

    private TextView tvName, tvEmail, tvRango, tvNivel, tvNumED , tvNumMD, tvNumLogros;
    private Button btnDinero, btnPuntosLC, btnPuntosMT, btnPuntosCS , btnPuntosCN , btnPuntosIN;

    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        tvName = (TextView) findViewById(R.id.tvUserNameProfile);
        tvEmail = (TextView) findViewById(R.id.tvUserEmailProfile);
        tvRango = (TextView) findViewById(R.id.tvRangoProfile);
        tvNivel = (TextView) findViewById(R.id.tvNivelProfile);
        tvNumED = (TextView) findViewById(R.id.tvNumExamDiag);
        tvNumMD = (TextView) findViewById(R.id.tvNumMeteoritos);
        tvNumLogros =(TextView) findViewById(R.id.tvNumLogros);

        btnDinero = (Button) findViewById(R.id.btnDinero);
        btnPuntosCN = (Button) findViewById(R.id.btnPuntosCN);
        btnPuntosLC = (Button) findViewById(R.id.btnPuntosLC);
        btnPuntosMT = (Button) findViewById(R.id.btnPuntosMT);
        btnPuntosCS = (Button) findViewById(R.id.btnPuntosCS);
        btnPuntosIN = (Button) findViewById(R.id.btnPuntosIN);


        pgbExperience = (ProgressBar) findViewById(R.id.pgbLevel);
        pgbMT = (ProgressBar) findViewById(R.id.pbmat);
        pgbCN = (ProgressBar) findViewById(R.id.pbcn);
        pgbCS = (ProgressBar) findViewById(R.id.pbcs);
        pgbLC = (ProgressBar) findViewById(R.id.pblc);
        pgbIN = (ProgressBar) findViewById(R.id.pbin);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String userSrting = bundle.getString("USER");
        User triaded = new Gson().fromJson(userSrting, User.class);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
        mDatabase.orderByKey().equalTo(triaded.getUserID()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                for (DataSnapshot message: dataSnapshot.getChildren())
                {
                    currentUser = message.getValue(User.class);
                    tvName.setText(currentUser.getUserName());
                    tvEmail.setText(currentUser.getUserEmail());
                    tvNivel.setText("Nivel : "+currentUser.getUserNivel());
                    tvRango.setText("Rango : "+currentUser.getUserRango());
                    tvNumED.setText(" "+currentUser.getNumExamDiagnostic());
                    tvNumMD.setText(" "+currentUser.getNumMeteoritosDestruidos());

                    btnDinero.setText("SaberMoney : "+currentUser.getUserDinero());
                    btnPuntosCN.setText(" "+currentUser.getPuntosCN()+" CN");
                    btnPuntosMT.setText(" "+currentUser.getPuntosMT()+" MT");
                    btnPuntosCS.setText(" "+currentUser.getPuntosCS()+" CS");
                    btnPuntosIN.setText(" "+currentUser.getPuntosIN()+" IN");
                    btnPuntosLC.setText(" "+currentUser.getPuntosLC()+" LC");

                    pgbExperience.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
                    pgbExperience.setProgress(currentUser.getNumExamDiagnostic());

                    pgbMT.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    pgbMT.setProgress(currentUser.getNumPreguntasMT());

                    pgbLC.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                    pgbLC.setProgress(currentUser.getNumPreguntasLC());

                    pgbCS.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
                    pgbCS.setProgress(currentUser.getNumPreguntasCS());

                    pgbIN.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
                    pgbIN.setProgress(currentUser.getNumPreguntasIN());

                    pgbCN.setProgressTintList(ColorStateList.valueOf(Color.CYAN));
                    pgbCN.setProgress(currentUser.getNumPreguntasCN());


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == android.R.id.home){
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this,intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
