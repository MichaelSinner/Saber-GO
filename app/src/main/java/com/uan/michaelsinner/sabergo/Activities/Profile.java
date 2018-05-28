package com.uan.michaelsinner.sabergo.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;

import java.util.Random;

public class Profile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private ProgressBar pgbExperience, pgbMT, pgbCN, pgbCS, pgbLC, pgbIN;
    private DatabaseReference mDatabase;
    private GoogleApiClient googleApiClient;
    private Random random;

    private TextView tvName, tvEmail, tvRango, tvNivel, tvNumED, tvNumMD, tvNumLogros;
    private TextView tvMT, tvIN, tvLC, tvCS, tvCN;
    private TextView tv01,tv02,tv03,tv04,tv05,tv06,tv07,tv08;
    private TextView tvExit, tvTip;
    private Button btnDinero, btnPuntosLC, btnPuntosMT, btnPuntosCS, btnPuntosCN, btnPuntosIN;
    private Button btnDelete, btnDeleteSi, btnDeleteNo;
    private ImageView ivImageProfile;
    private Dialog dialog;

    private User currentUser;
    private Uri photoUrl;
    private String providerId;
    private String[] tipsList = {"'Lee atentamente cada pregunta y cada opción de principio a fin, evita sacar conclusiones precipitadas, esto porque cuando se están creando los exámenes, las preguntas señuelo, se hacen con el propósito de confundir'",
            "'Responde primero a las preguntas que se puede dar una certeza de la respuesta por su facilidad y después, a las preguntas que consideres difícil.'",
            "'En las preguntas más difíciles, elimina las respuestas que son evidentemente erróneas, relee la pregunta y cada una de las respuestas restantes.'",
            "'Pon atención a las palabras 'completamente' 'indiscutiblemente' 'siempre' 'jamás' 'nunca' en la formulación de la pregunta, significa que la respuesta correcta debería de ser un hecho irrefutable. Si las palabras son utilizadas en las respuestas, significa que la respuesta debe ser un hecho incontestable, y que solamente puede ser verdadero o falso.'",
            "'Examina las respuestas que enuncien las expresiones “todas las respuestas anteriores” o “cada una de las anteriores”. Nunca consideres este tipo de respuestas como la respuesta correcta hasta no haber analizado si todos los enunciados, pese a ser verdaderos, tengan una relación real con la pregunta.'",
            "'Ten especial cuidado con las preguntas que contienen “NO” o “EXCEPTO”, aún más cuando se combinan y resultan en una afirmación, ya que es común errar desde el inicio y suele ser un elemento de distracción.'",
            "'Modificar la respuesta si se encuentra una buena razón para hacerlo. En caso de no tener certeza de la respuesta, elige la que más crea ya que tiene un 25 % de probabilidad de acertar.'",
            "'Siempre es mejor una respuesta probable que una pregunta sin contestar, conteste todas las preguntas.'"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Perfil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dialog = new Dialog(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        com.getbase.floatingactionbutton.FloatingActionButton fab_logros = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_logros);
        com.getbase.floatingactionbutton.FloatingActionButton fab_tips = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.fab_tips);

        Typeface font2 = Typeface.createFromAsset(getAssets(),"fonts/IndieFlower.ttf");



        fab_logros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,Achievements.class);
                startActivity(intent);
            }
        });

        fab_tips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                random = new Random();
                int index = random.nextInt(tipsList.length-1);

                dialog.setContentView(R.layout.pop_tip);
                tvTip = (TextView) dialog.findViewById(R.id.str_Tip);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);
                tvTip.setText(tipsList[index]);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

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

        tvMT = (TextView) findViewById(R.id.textPGMT);
        tvMT.setTypeface(font2);
        tvLC = (TextView) findViewById(R.id.textPGLC);
        tvLC.setTypeface(font2);
        tvCS = (TextView) findViewById(R.id.textPGCS);
        tvCS.setTypeface(font2);
        tvCN = (TextView) findViewById(R.id.textPGCN);
        tvCN.setTypeface(font2);
        tvIN = (TextView) findViewById(R.id.textPGIN);
        tvIN.setTypeface(font2);


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
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnDelete.setTypeface(font2);


        pgbExperience = (ProgressBar) findViewById(R.id.pgbLevel);
        pgbMT = (ProgressBar) findViewById(R.id.pbmat);
        pgbCN = (ProgressBar) findViewById(R.id.pbcn);
        pgbCS = (ProgressBar) findViewById(R.id.pbcs);
        pgbLC = (ProgressBar) findViewById(R.id.pblc);
        pgbIN = (ProgressBar) findViewById(R.id.pbin);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String userSrting = bundle.getString("USER");
        final User user = new Gson().fromJson(userSrting, User.class);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
        mDatabase.orderByKey().equalTo(user.getUserID()).addValueEventListener(new ValueEventListener() {
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

                    pgbMT.setProgressTintList(ColorStateList.valueOf(Color.BLUE));
                    pgbMT.setProgress(currentUser.getNumPreguntasMT());

                    pgbLC.setProgressTintList(ColorStateList.valueOf(Color.MAGENTA));
                    pgbLC.setProgress(currentUser.getNumPreguntasLC());

                    pgbCS.setProgressTintList(ColorStateList.valueOf(Color.rgb(116,31,0)));
                    pgbCS.setProgress(currentUser.getNumPreguntasCS());

                    pgbIN.setProgressTintList(ColorStateList.valueOf(Color.RED));
                    pgbIN.setProgress(currentUser.getNumPreguntasIN());

                    pgbCN.setProgressTintList(ColorStateList.valueOf(Color.rgb(15,180,0)));
                    pgbCN.setProgress(currentUser.getNumPreguntasCN());

                    tvMT.setText(currentUser.getNumPreguntasMT()+"/100");
                    tvLC.setText(currentUser.getNumPreguntasLC()+"/100");
                    tvCS.setText(currentUser.getNumPreguntasCS()+"/100");
                    tvCN.setText(currentUser.getNumPreguntasCN()+"/100");
                    tvIN.setText(currentUser.getNumPreguntasIN()+"/100");


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        loadImageProfile();

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.setContentView(R.layout.pop_delete);
                btnDeleteSi = (Button) dialog.findViewById(R.id.btnDelete_Si);
                btnDeleteNo = (Button) dialog.findViewById(R.id.btnDelete_No);

                btnDeleteSi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteAcount(user);
                    }
                });

                btnDeleteNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
      if (id == android.R.id.home) {
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

    public void deleteAcount(User user)
    {
        FirebaseDatabase.getInstance().getReference().child("Student").child(user.getUserID()).removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                exitApp();
            }
        });


    }

    private void exitApp() {

        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else {
                    Toast.makeText(getApplicationContext(), "Error al Revocar la sesión Google", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void goLoginScreen() {
        final Intent intent = new Intent(this, Login.class);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

