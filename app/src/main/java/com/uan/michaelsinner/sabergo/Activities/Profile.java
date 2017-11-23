package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class Profile extends AppCompatActivity {
    private ProgressBar pgbExperience, pgbMT, pgbCN, pgbCS, pgbLC, pgbIN;
    private DatabaseReference mDatabase;

    private TextView tvName, tvEmail, tvRango, tvNivel, tvNumED, tvNumMD, tvNumLogros;
    private TextView tv01,tv02,tv03,tv04,tv05,tv06,tv07,tv08;
    private Button btnDinero, btnPuntosLC, btnPuntosMT, btnPuntosCS, btnPuntosCN, btnPuntosIN;
    private ImageView ivImageProfile;

    private User currentUser;
    private Uri photoUrl;
    private String providerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);




        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Achievements.class);
                startActivity(intent);
            }
        });

        ivImageProfile = (ImageView) findViewById(R.id.ivImageProfilePerfil);

        tvName = (TextView) findViewById(R.id.tvUserNameProfile);
        tvName.setTypeface(font2);
        tvEmail = (TextView) findViewById(R.id.tvUserEmailProfile);
        tvEmail.setTypeface(font2);
        tvRango = (TextView) findViewById(R.id.tvRangoProfile);
        tvRango.setTypeface(font2);
        tvNivel = (TextView) findViewById(R.id.tvNivelProfile);
        tvNivel.setTypeface(font2);
        tvNumED = (TextView) findViewById(R.id.tvNumExamDiag);
        tvNumED.setTypeface(font2);
        tvNumMD = (TextView) findViewById(R.id.tvNumMeteoritos);
        tvNumMD.setTypeface(font2);
        tvNumLogros = (TextView) findViewById(R.id.tvNumLogros);
        tvNumLogros.setTypeface(font2);

        tv01 = (TextView) findViewById(R.id.tvText01);
        tv01.setTypeface(font2);
        tv02 = (TextView) findViewById(R.id.tvText02);
        tv02.setTypeface(font2);
        tv03 = (TextView) findViewById(R.id.tvText03);
        tv03.setTypeface(font2);
        tv04 = (TextView) findViewById(R.id.tvText04);
        tv04.setTypeface(font2);
        tv05 = (TextView) findViewById(R.id.tvText05);
        tv05.setTypeface(font2);
        tv06 = (TextView) findViewById(R.id.tvText06);
        tv06.setTypeface(font2);
        tv07 = (TextView) findViewById(R.id.tvText07);
        tv07.setTypeface(font2);
        tv08 = (TextView) findViewById(R.id.tvText08);
        tv08.setTypeface(font2);


        btnDinero = (Button) findViewById(R.id.btnDinero);
        btnDinero.setTypeface(font2);
        btnPuntosCN = (Button) findViewById(R.id.btnPuntosCN);
        btnPuntosCN.setTypeface(font2);
        btnPuntosLC = (Button) findViewById(R.id.btnPuntosLC);
        btnPuntosLC.setTypeface(font2);
        btnPuntosMT = (Button) findViewById(R.id.btnPuntosMT);
        btnPuntosMT.setTypeface(font2);
        btnPuntosCS = (Button) findViewById(R.id.btnPuntosCS);
        btnPuntosCS.setTypeface(font2);
        btnPuntosIN = (Button) findViewById(R.id.btnPuntosIN);
        btnPuntosIN.setTypeface(font2);


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
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot message : dataSnapshot.getChildren()) {
                    currentUser = message.getValue(User.class);
                    tvName.setText(currentUser.getUserName());
                    tvEmail.setText(currentUser.getUserEmail());
                    tvNivel.setText("Nivel : " + currentUser.getUserNivel());
                    tvRango.setText("Rango : " + currentUser.getUserRango());
                    tvNumED.setText(" " + currentUser.getNumExamDiagnostic());
                    tvNumMD.setText(" " + currentUser.getNumMeteoritosDestruidos());
                    tvNumLogros.setText(" "+currentUser.getNumLogros()+"/9");

                    btnDinero.setText("SaberMoney : " + currentUser.getUserDinero());
                    btnPuntosCN.setText(" " + currentUser.getPuntosCN() + " CN");
                    btnPuntosMT.setText(" " + currentUser.getPuntosMT() + " MT");
                    btnPuntosCS.setText(" " + currentUser.getPuntosCS() + " CS");
                    btnPuntosIN.setText(" " + currentUser.getPuntosIN() + " IN");
                    btnPuntosLC.setText(" " + currentUser.getPuntosLC() + " LC");

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

        loadImageProfile();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadImageProfile(){
        FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
        final String ID = userF.getUid();
        if (userF != null) {
            for (UserInfo profile : userF.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();
                photoUrl = profile.getPhotoUrl();
//                    Log.e(TAG, String.valueOf(photoUrl));

            }
        }

        // Glide.with(this).load(photoUrl).fitCenter().override(120,120).into(ivUserImagProfile);

        if(providerId.equals("google.com") || providerId.equals("facebook.com")){
            Glide.with(this).load(photoUrl).asBitmap().into(new BitmapImageViewTarget(ivImageProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivImageProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
        }else{
            Glide.with(this).load(R.drawable.img_profile_default).asBitmap().into(new BitmapImageViewTarget(ivImageProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                            RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivImageProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
        }

    }
}
