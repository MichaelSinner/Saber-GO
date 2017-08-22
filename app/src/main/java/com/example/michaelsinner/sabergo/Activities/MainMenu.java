package com.example.michaelsinner.sabergo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.Data.User;
import com.example.michaelsinner.sabergo.R;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Map;


public class MainMenu extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private Button btnPruebaDiagn;
    private Button btnPruebaDiaria;
    private Button btnSimulacro;
    private Button btnPerfil;

    private String userName, userEmail;
    private User user;

    private ImageView ivUserImagProfile;
    TextView tvUserName, tvUserEmail, tvUserLevel, prueba;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference mDatabase;


    private GoogleApiClient googleApiClient;
    private static final String TAG ="error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menú Principal");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View viewNav = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);


        tvUserEmail = (TextView)  viewNav.findViewById(R.id.tvUserEmailMain);
        tvUserName = (TextView) viewNav.findViewById(R.id.tvUserNameMain);
        tvUserLevel = (TextView) viewNav.findViewById(R.id.tvUserNivel);
        prueba = (TextView) findViewById(R.id.tvPruebaMenu);



        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        final String ID = (String) bundle.get("ID");
        final String email = (String) bundle.get("EMAIL");
        final String name = (String) bundle.get("NOMBRE");
        final String image = (String) bundle.get("IMAGE");

        //boolean exist;
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        //exist = mDatabase.child("Student").child(ID).child("userEmail").toString();
        //exist = mDatabase.child("Student").child(ID).child("userID").equals(ID);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");

        mDatabase.orderByKey().equalTo(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){
                    Log.e(TAG," exist : "+dataSnapshot.exists()+" | "+dataSnapshot.getChildren());
                    user = new User(ID, email, "pass", name, image, 1, "Recluta", 0, 0, 0, 0, 0, 1000, 1, 0, 0, 0, 0, 0, 0, 0);

                    mDatabase.child(ID).setValue(user);
                    tvUserName.setText(user.getUserName());
                    tvUserEmail.setText(user.getUserEmail());
                    tvUserLevel.setText("Nivel : "+user.getUserNivel());
                    prueba.setText(user.getUserID());
                    Log.e(TAG,"Estudiante creado : "+user.toString());
                }else{
                    Log.e(TAG," exist : "+dataSnapshot.exists()+" | "+dataSnapshot.getChildren());
                    for (DataSnapshot message: dataSnapshot.getChildren())
                    {
                        user = message.getValue(User.class);
                        tvUserName.setText(user.getUserName());
                        tvUserEmail.setText(user.getUserEmail());
                        tvUserLevel.setText("Nivel : "+user.getUserNivel());
                        prueba.setText(user.getUserID());
                        Log.e(TAG,"Estudiante Capturado : "+user.toString());
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

/*
        if(exist){
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Student").child(ID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    user = new User();

                    String uID = (String) dataSnapshot.child("userID").getValue();

                    user.setUserID(uID);
                    String uName = (String) dataSnapshot.child("userName").getValue();
                    Log.e(TAG," name : "+uName);
                    user.setUserName(uName);
                    String uEmail = (String) dataSnapshot.child("userEmail").getValue();
                    user.setUserEmail(uEmail);

                    Log.e(TAG,"Estudiante capturado : "+user.toString());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }else{
            user = new User(ID, email, "pass", name, image, 1, "Recluta", 0, 0, 0, 0, 0, 1000, 1, 0, 0, 0, 0, 0, 0, 0);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Student").child(ID).setValue(user);
            Log.e(TAG,"Estudiante creado : "+user.toString());



        }
*/

        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                FirebaseUser userf = firebaseAuth.getCurrentUser();

                if(userf!=null && user!=null){


                }else{
                    goLoginScreen();;
                }
            }
        };



        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();





        btnPruebaDiagn = (Button) findViewById(R.id.btnPruebaDiagnostico);
        btnPruebaDiagn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (isOnline(getApplicationContext())){
                   startActivity(toPruebaDiagnostico());
               }else{
                   startActivity(toNoInternet());
               }

            }
        });

        btnPruebaDiaria = (Button) findViewById(R.id.btnPruebasDiarias);
        btnPruebaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (isOnline(getApplicationContext())){
                    startActivity(toPruebaEntrenamiento());
                }else{
                    startActivity(toNoInternet());
                }
            }
        });

        btnSimulacro = (Button) findViewById(R.id.btnMeteoros);
        btnSimulacro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline(getApplicationContext()))
                {
                    startActivity(toModuloLudica());
                }else{
                    startActivity(toNoInternet());
                }
            }
        });

        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getApplicationContext()))
                {
                    startActivity(toPerfil(user));
                }else{
                    startActivity(toNoInternet());
                }
            }
        });



    }


    public void CrearUsuario(String userID,String userEmail ,String name, String imageProfile)
    {



    }

    public void loadPerfil(final String UID, final String email, final String name, final String imageProfile)
    {

        mDatabase = FirebaseDatabase.getInstance().getReference();


        mDatabase.child("Student").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean exist = mDatabase.child("Student").child(UID).child("userID").equals(UID);
                for (DataSnapshot child : dataSnapshot.getChildren()){
                    if (!exist){
                        User newUser = new User(UID,email,"pass",name,imageProfile,1,"Recluta",0,0,0,0,0,1000,1,0,1,7,2,3,1,1);
                        mDatabase.child("Student").child(UID).setValue(newUser);
                        exist = true;
                    } else {
                        Object object = child.getValue();
                        //String uID = ((Map)object).get("userID").toString();
                        String uID = (String) child.child("userID").getValue();
                        String uName = ((Map)object).get("userName").toString();
                        String uEmail = ((Map)object).get("userEmail").toString();
                        String uLevel = ((Map)object).get("userNivel").toString();
                        prueba.setText(uID);
                        tvUserName.setText(uName);
                        tvUserEmail.setText(uEmail);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /*
    public void loadProfile(final String ID, final String email, final String name,final String imageProfile)
    {

        mDatabase = FirebaseDatabase.getInstance().getReference();

        Log.e(TAG,"mensjae importante si es true : "+mDatabase.child("Student").child(ID).getKey());

        boolean exist = mDatabase.child("Student").child(ID).equals(ID);
        if(!exist){
            User user = new User(ID,email,"pass",name,imageProfile,1,"Recluta",0,0,0,0,0,1000,1,0,1,7,2,3,1,1);
            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Student").child(ID).setValue(user);
            tvUserName.setText(name);
            tvUserEmail.setText(email);
            prueba.setText(ID);
            exist = true;
        }else{


            mDatabase = FirebaseDatabase.getInstance().getReference();
            mDatabase.child("Student").child(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {



                } for (DataSnapshot dtSnapshot1: dataSnapshot.getChildren())
                    {
                        User user = new User();
                        user.setUserID((String) dtSnapshot1.child("userID").getValue());
                        user.setUserNivel((Integer) dtSnapshot1.child("userNivel").getValue());
                        user.setDoExamDiagnostic((Boolean) dtSnapshot1.child("doExamDiagnostic").getValue());
                        user.setUserName((String) dtSnapshot1.child("userName").getValue());
                        user.setUserEmail((String) dtSnapshot1.child("userEmail").getValue());
                        user.setUserNivel((Integer) dtSnapshot1.child("userNivel").getValue());
                        user.setUserRango((String) dtSnapshot1.child("userRango").getValue());
                        user.setUserDinero((Integer) dtSnapshot1.child("userDinero").getValue());
                        user.setProgressLevelExp((Integer) dtSnapshot1.child("progressLevelExp").getValue());
                        user.setNumMeteoritosDestruidos((Integer) dtSnapshot1.child("numMeteoritosDrestruidos").getValue());
                        user.setNumExamDiagnostic((Integer) dtSnapshot1.child("numExamDiagnostic").getValue());
                        user.setPuntosCN((Integer) dtSnapshot1.child("puntosCN").getValue());
                        user.setPuntosCS((Integer) dtSnapshot1.child("puntosCS").getValue());
                        user.setPuntosIN((Integer) dtSnapshot1.child("puntosIN").getValue());
                        user.setPuntosLC((Integer) dtSnapshot1.child("puntosLC").getValue());
                        user.setPuntosMT((Integer) dtSnapshot1.child("puntosMT").getValue());
                        user.setNumPreguntasCN((Integer) dtSnapshot1.child("numPreguntasCN").getValue());
                        user.setNumPreguntasCS((Integer) dtSnapshot1.child("numPreguntasCS").getValue());
                        user.setNumPreguntasIN((Integer) dtSnapshot1.child("numPreguntasIN").getValue());
                        user.setNumPreguntasLC((Integer) dtSnapshot1.child("numPreguntasLC").getValue());
                        user.setNumPreguntasMT((Integer) dtSnapshot1.child("numPreguntasMT").getValue());

                        Log.e(TAG,"Usuario recogido : "+user.toString());

                    }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }


    }
    */

    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(fireAuthStateListener);

        /*
        mDatabase = FirebaseDatabase.getInstance().getReference();
        ValueEventListener valueEventListener = mDatabase.child("Student").child(ID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dtSnapshot1: dataSnapshot.getChildren())
                {
                    User user = new User();
                    user.setUserID((String) dtSnapshot1.child("userID").getValue());
                    user.setUserNivel((Integer) dtSnapshot1.child("userNivel").getValue());
                    user.setDoExamDiagnostic((Boolean) dtSnapshot1.child("doExamDiagnostic").getValue());
                    user.setUserName((String) dtSnapshot1.child("userName").getValue());
                    user.setUserEmail((String) dtSnapshot1.child("userEmail").getValue());
                    user.setUserNivel((Integer) dtSnapshot1.child("userNivel").getValue());
                    user.setUserRango((String) dtSnapshot1.child("userRango").getValue());
                    user.setUserDinero((Integer) dtSnapshot1.child("userDinero").getValue());
                    user.setProgressLevelExp((Integer) dtSnapshot1.child("progressLevelExp").getValue());
                    user.setNumMeteoritosDestruidos((Integer) dtSnapshot1.child("numMeteoritosDrestruidos").getValue());
                    user.setNumExamDiagnostic((Integer) dtSnapshot1.child("numExamDiagnostic").getValue());
                    user.setPuntosCN((Integer) dtSnapshot1.child("puntosCN").getValue());
                    user.setPuntosCS((Integer) dtSnapshot1.child("puntosCS").getValue());
                    user.setPuntosIN((Integer) dtSnapshot1.child("puntosIN").getValue());
                    user.setPuntosLC((Integer) dtSnapshot1.child("puntosLC").getValue());
                    user.setPuntosMT((Integer) dtSnapshot1.child("puntosMT").getValue());
                    user.setNumPreguntasCN((Integer) dtSnapshot1.child("numPreguntasCN").getValue());
                    user.setNumPreguntasCS((Integer) dtSnapshot1.child("numPreguntasCS").getValue());
                    user.setNumPreguntasIN((Integer) dtSnapshot1.child("numPreguntasIN").getValue());
                    user.setNumPreguntasLC((Integer) dtSnapshot1.child("numPreguntasLC").getValue());
                    user.setNumPreguntasMT((Integer) dtSnapshot1.child("numPreguntasMT").getValue());

                    Log.e(TAG,"Usuario recogido : "+user.toString());
                    tvUserEmail.setText(user.getUserEmail());
                    tvUserName.setText(user.getUserName());
                    tvUserLevel.setText("Nivel :"+user.getUserNivel());
                    prueba.setText(user.getUserID());

                }








                tvUserEmail.setText(user.getUserEmail());
                tvUserName.setText(user.getUserName());
                tvUserLevel.setText("Nivel :"+user.getUserNivel());
                prueba.setText(user.getUserID());

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mDatabase.addValueEventListener(valueEventListener);
        */
    }



    @Override
    protected void onStop() {
        super.onStop();
        if(firebaseAuth != null) firebaseAuth.removeAuthStateListener(fireAuthStateListener);
    }

    public static Bitmap getFacebookProfilePicture(String userID) throws SocketException, SocketTimeoutException, MalformedURLException, IOException, Exception
    {
        String imageURL;
        Bitmap bitmap = null;
        imageURL = "http://graph.facebook.com/"+userID+"/picture?type=large";
        InputStream in = (InputStream) new URL(imageURL).getContent();
        bitmap = BitmapFactory.decodeStream(in);

        return bitmap;
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.itmPruebaDiagnostico)
        {
            if(isOnline(getApplicationContext()))
            {
                startActivity(toPruebaDiagnostico());
            }else{
                startActivity(toNoInternet());
            }
        } else if (id == R.id.itmPruebaDiaria)
        {
            if(isOnline(getApplicationContext()))
            {
                startActivity(toPruebaEntrenamiento());
            }else{
                startActivity(toNoInternet());
            }
        } else if (id == R.id.itmLudica)
        {
            if(isOnline(getApplicationContext()))
            {
                startActivity(toModuloLudica());
            }else{
                startActivity(toNoInternet());
            }
        } else if (id == R.id.itmLogros) {
            startActivity(toAchievements());
        } else if (id == R.id.itmSettings) {
            startActivity(toSettings());
        } else if (id == R.id.itmAbout) {
            startActivity(toAbout());
        } else if (id == R.id.itmExit) {
            exitApp();
        } else if (id == R.id.itmTutorial){
            startActivity(toTutorial());
        }else if (id == R.id.nav_share)
        {
            if(isOnline(getApplicationContext()))
            {
                startActivity(toShare());
            }else{
                startActivity(toNoInternet());
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();

    }

    private Intent toNoInternet()
    {
        Intent toNoInternet = new Intent(MainMenu.this, NoInternet.class);
        return  toNoInternet;
    }
    private Intent toPruebaDiagnostico()
    {
        Intent toPruebaDiagnostico = new Intent(MainMenu.this, PruebaDiagnostico.class);
        return  toPruebaDiagnostico;
    }
    private Intent toPruebaEntrenamiento(){
        Intent toPruebaEntrenamiento = new Intent(MainMenu.this, ModuloPruebasDiarias.class);
        return  toPruebaEntrenamiento;
    }
    private Intent toModuloLudica(){
        Intent toModuloLudica = new Intent(MainMenu.this, ModuloAR.class);
        return  toModuloLudica;
    }

    public Intent toTutorial()
    {
        Intent toTutorial = new Intent(MainMenu.this, Tutorial.class);
        return toTutorial;
    }

    public Intent toPerfil(User userSended)
    {
        Intent toPerfil = new Intent(MainMenu.this, Profile.class);
        toPerfil.putExtra("ID",userSended.getUserID());
        toPerfil.putExtra("NOMBRE",userSended.getUserName());
        toPerfil.putExtra("EMAIL",userSended.getUserEmail());
        toPerfil.putExtra("IMAGE",userSended.getUserImageProfile());
        toPerfil.putExtra("RANGO",userSended.getUserRango());
        toPerfil.putExtra("NIVEL",userSended.getUserNivel());
        toPerfil.putExtra("PRB_EXP",userSended.getProgressLevelExp());
        toPerfil.putExtra("DO_ED",userSended.isDoExamDiagnostic());
        toPerfil.putExtra("PUNTOS_LC",userSended.getPuntosLC());
        toPerfil.putExtra("PUNTOS_MT",userSended.getPuntosMT());
        toPerfil.putExtra("PUNTOS_CN",userSended.getPuntosCN());
        toPerfil.putExtra("PUNTOS_CS",userSended.getPuntosCS());
        toPerfil.putExtra("PUNTOS_IN",userSended.getPuntosIN());
        toPerfil.putExtra("MONEY",userSended.getUserDinero());
        toPerfil.putExtra("NUM_ED",userSended.getNumExamDiagnostic());
        toPerfil.putExtra("NUM_MD",userSended.getNumMeteoritosDestruidos());

        toPerfil.putExtra("NUM_LC",userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_MT",userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_CS",userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_CN",userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_IN",userSended.getUserImageProfile());


        startActivity(toPerfil);


        return toPerfil;
    }
    public Intent toAbout(){
        Intent toAbout = new Intent(MainMenu.this, About.class);
        return  toAbout;
    }

    public  Intent toAchievements(){
        Intent toAchievements = new Intent(MainMenu.this, Achievements.class);
        return  toAchievements;
    }

    public  Intent toShare(){
        Intent toShare = new Intent(MainMenu.this, Share.class);
        return  toShare;
    }
    private void goLoginScreen()
    {
        final Intent intent = new Intent(this, Login.class);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Error al cerrar sesion Google",Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public Intent toSettings(){
        Intent toSettings = new Intent(MainMenu.this, Settings.class);
        return  toSettings;
    }
    private void exitApp(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(getApplicationContext(),"Error al cerrar sesion Google",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void revoke(View view){
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if(status.isSuccess()){
                    goLoginScreen();
                }else{
                    Toast.makeText(getApplicationContext(),"Error al revocar sesion Google",Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}

