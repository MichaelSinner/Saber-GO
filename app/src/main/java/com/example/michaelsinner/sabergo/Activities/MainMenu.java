package com.example.michaelsinner.sabergo.Activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.michaelsinner.sabergo.Data.User;
import com.example.michaelsinner.sabergo.R;
import com.facebook.login.LoginManager;
import com.firebase.ui.storage.images.FirebaseImageLoader;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private Button btnPruebaDiagn;
    private Button btnPruebaDiaria;
    private Button btnSimulacro;
    private Button btnPerfil;

    private Bundle allInfo;


    private String userName, userEmail;
    private User user;

    private ImageView ivUserImagProfile;
    Uri photoUrl;
    TextView tvUserName, tvUserEmail, tvUserLevel, prueba, tvPruebaUpdate;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private DatabaseReference mDatabase;
    private DatabaseReference userReference;


    private GoogleApiClient googleApiClient;
    private static final String TAG = "error";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menú Principal");

        Log.e(TAG, "On Create");

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestEmail().build();

        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View viewNav = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");
        Typeface font2 = Typeface.createFromAsset(getAssets(), "fonts/IndieFlower.ttf");


        tvUserEmail = (TextView) viewNav.findViewById(R.id.tvUserEmailMain);
        tvUserName = (TextView) viewNav.findViewById(R.id.tvUserNameMain);
        tvUserLevel = (TextView) viewNav.findViewById(R.id.tvUserNivel);
        prueba = (TextView) findViewById(R.id.tvPruebaMenu);
        tvPruebaUpdate = (TextView) findViewById(R.id.tvPruebaUpdate);
        ivUserImagProfile = (ImageView) viewNav.findViewById(R.id.imgProfileMenu);


        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser userf = firebaseAuth.getCurrentUser();

                if (userf != null && user != null) {
                    Log.e(TAG, "cambiosito");

                } else {
                    goLoginScreen();

                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference("Student");

        String stringUser;
        allInfo = getIntent().getExtras();
        stringUser = allInfo.getString("USER");

        user = new Gson().fromJson(stringUser, User.class);


        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        btnPruebaDiagn = (Button) findViewById(R.id.btnPruebaDiagnostico);
        btnPruebaDiagn.setTypeface(font2);
        btnPruebaDiagn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getApplicationContext())) {
                    startActivity(toPruebaDiagnostico());
                } else {
                    startActivity(toNoInternet());
                }

            }
        });

        btnPruebaDiaria = (Button) findViewById(R.id.btnPruebasDiarias);
        btnPruebaDiaria.setTypeface(font2);
        btnPruebaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getApplicationContext())) {
                    startActivity(toPruebaEntrenamiento());
                } else {
                    startActivity(toNoInternet());
                }
            }
        });

        btnSimulacro = (Button) findViewById(R.id.btnMeteoros);
        btnSimulacro.setTypeface(font2);
        btnSimulacro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOnline(getApplicationContext())) {
                    //startActivity(toModuloLudica());
                    updateProfile(user.getUserID());

                } else {
                    startActivity(toNoInternet());
                }
            }
        });

        btnPerfil = (Button) findViewById(R.id.btnPerfil);
        btnPerfil.setTypeface(font2);
        btnPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline(getApplicationContext())) {
                    startActivity(toPerfil(user));
                } else {
                    startActivity(toNoInternet());
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "ON Resume");


            /*
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            */

    }


    public boolean updateProfile(String id) {

        tvPruebaUpdate = (TextView) findViewById(R.id.tvPruebaUpdate);
        //allInfo = getIntent().getExtras();
        //stringUser = allInfo.getString("USER");
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Student").child(id);
        //User userUpdated =new Gson().fromJson(stringUser, User.class);
        int numAcum = user.getNumExamDiagnostic();
        numAcum++;
        user.setNumExamDiagnostic(numAcum);
        df.setValue(user);

        // tvPruebaUpdate.setText(user.getNumExamDiagnostic());


        Toast.makeText(getApplicationContext(), "Información usuario actualizado", Toast.LENGTH_SHORT).show();
        return true;
    }

    //public void


    /*
    public void loadPerfil(final String UID)
    {

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Student");
        mDatabase.orderByKey().equalTo(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(!dataSnapshot.exists()){

                }else{

                    for (DataSnapshot message: dataSnapshot.getChildren())
                    {
                        user = message.getValue(User.class);


                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

*/
    @Override
    protected void onStart() {
        super.onStart();
        //firebaseAuth.addAuthStateListener(fireAuthStateListener);
        Log.e(TAG, "On Start");
/*
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = null;
                for (DataSnapshot message: dataSnapshot.getChildren()){
                    user = message.getValue(User.class);
                    tvUserName.setText(user.getUserName());
                    tvUserEmail.setText(user.getUserEmail());
                    tvUserLevel.setText("Nivel : "+user.getUserNivel());
                    prueba.setText(user.getUserID());
                    tvPruebaUpdate.setText(user.getNumExamDiagnostic());
                    return;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        */


        if (allInfo != null) {

            final String email = (String) allInfo.get("EMAIL");
            final String name = (String) allInfo.get("NOMBRE");



            final String image = (String) allInfo.get("IMAGE");

            FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
            final String ID = userF.getUid();
            if (userF != null) {
                for (UserInfo profile : userF.getProviderData()) {
                    // Id of the provider (ex: google.com)
                    String providerId = profile.getProviderId();

                    photoUrl = profile.getPhotoUrl();
//                    Log.e(TAG, String.valueOf(photoUrl));
                };


            }

           // Glide.with(this).load(photoUrl).fitCenter().override(120,120).into(ivUserImagProfile);

            Glide.with(this).load(photoUrl).asBitmap().into(new BitmapImageViewTarget(ivUserImagProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                    RoundedBitmapDrawableFactory.create(getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivUserImagProfile.setImageDrawable(circularBitmapDrawable);
                }
            });

            mDatabase = FirebaseDatabase.getInstance().getReference();

            userReference = mDatabase.child("Student");




            userReference.orderByKey().equalTo(ID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (!dataSnapshot.exists()) {
                        user = new User(ID, email, "pass", name, image, 1, "Recluta", 0, 0, 0, 0, 0, 1000, 0, 0, 0, 0, 0, 0, 0, 0, false);
                        //user = new User(ID, email, "pass", name, image, 1, "Recluta", 0, 0, 0, 0, 0, 1000, 1, 0, 0, 0, 0, 0, 0, 0);

                        userReference.child(ID).setValue(user);
                        tvUserName.setText(user.getUserName());
                        tvUserEmail.setText(user.getUserEmail());
                        tvUserLevel.setText("Nivel : " + user.getUserNivel());
                        prueba.setText(user.getUserID());
                        Log.e(TAG, "Estudiante creado : " + user.toString());
                        return;
                    } else {

                        Log.e(TAG, " exist : " + dataSnapshot.exists() + " | " + dataSnapshot.getChildren());
                        for (DataSnapshot message : dataSnapshot.getChildren()) {
                            user = message.getValue(User.class);
                            tvUserName.setText(user.getUserName());
                            tvUserEmail.setText(user.getUserEmail());
                            tvUserLevel.setText("Nivel : " + user.getUserNivel());
                            prueba.setText(user.getUserID());
                            tvPruebaUpdate.setText(String.valueOf(user.getNumExamDiagnostic()));
                            Log.e(TAG, "Estudiante Capturado de fire : " + user.toString());
                            return;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {

            // user = new Gson().fromJson(stringUser, User.class);
            Log.e(TAG, "NO entro");
            if (user != null) {

                String ID = user.getUserID();
                tvUserName.setText(user.getUserName());
                tvUserEmail.setText(user.getUserEmail());
                tvUserLevel.setText("Nivel : " + user.getUserNivel());
                prueba.setText(user.getUserID());
                Log.e(TAG, "Estudiante Capturado del bundle: " + user.toString());
//                loadPerfil(ID);

            } else {

                goLoginScreen();
            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (firebaseAuth != null) firebaseAuth.removeAuthStateListener(fireAuthStateListener);
    }

    public static Bitmap getFacebookProfilePicture(String userID) throws SocketException, SocketTimeoutException, MalformedURLException, IOException, Exception {
        String imageURL;
        Bitmap bitmap = null;
        imageURL = "http://graph.facebook.com/" + userID + "/picture?type=large";
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

        if (id == R.id.itmPruebaDiagnostico) {
            if (isOnline(getApplicationContext())) {
                startActivity(toPruebaDiagnostico());
            } else {
                startActivity(toNoInternet());
            }
        } else if (id == R.id.itmPruebaDiaria) {
            if (isOnline(getApplicationContext())) {
                startActivity(toPruebaEntrenamiento());
            } else {
                startActivity(toNoInternet());
            }
        } else if (id == R.id.itmLudica) {
            if (isOnline(getApplicationContext())) {
                startActivity(toModuloLudica());
            } else {
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
        } else if (id == R.id.itmTutorial) {
            startActivity(toTutorial());
        } else if (id == R.id.nav_share) {
            if (isOnline(getApplicationContext())) {
                startActivity(toShare());
            } else {
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

    private Intent toNoInternet() {
        Intent toNoInternet = new Intent(MainMenu.this, NoInternet.class);
        return toNoInternet;
    }

    private Intent toPruebaDiagnostico() {
        Intent toPruebaDiagnostico = new Intent(MainMenu.this, PruebaDiagnostico.class);
        toPruebaDiagnostico.putExtra("ID", user.getUserID());
        toPruebaDiagnostico.putExtra("USER", new Gson().toJson(user));
        return toPruebaDiagnostico;
    }

    private Intent toPruebaEntrenamiento() {
        Intent toPruebaEntrenamiento = new Intent(MainMenu.this, ModuloPruebasDiarias.class);
        toPruebaEntrenamiento.putExtra("USER", new Gson().toJson(user));
        return toPruebaEntrenamiento;
    }

    private Intent toModuloLudica() {
        Intent toModuloLudica = new Intent(MainMenu.this, ModuloAR.class);
        return toModuloLudica;
    }

    public Intent toTutorial() {
        Intent toTutorial = new Intent(MainMenu.this, Tutorial.class);
        return toTutorial;
    }

    public Intent toPerfil(User userSended) {
        Intent toPerfil = new Intent(MainMenu.this, Profile.class);
        toPerfil.putExtra("USER", new Gson().toJson(userSended));
        toPerfil.putExtra("ID", userSended.getUserID());
        toPerfil.putExtra("NOMBRE", userSended.getUserName());
        toPerfil.putExtra("EMAIL", userSended.getUserEmail());
        toPerfil.putExtra("IMAGE", userSended.getUserImageProfile());
        toPerfil.putExtra("RANGO", userSended.getUserRango());
        toPerfil.putExtra("NIVEL", userSended.getUserNivel());
        toPerfil.putExtra("PRB_EXP", userSended.getProgressLevelExp());
        toPerfil.putExtra("DO_ED", userSended.isDoExamDiagnostic());
        toPerfil.putExtra("PUNTOS_LC", userSended.getPuntosLC());
        toPerfil.putExtra("PUNTOS_MT", userSended.getPuntosMT());
        toPerfil.putExtra("PUNTOS_CN", userSended.getPuntosCN());
        toPerfil.putExtra("PUNTOS_CS", userSended.getPuntosCS());
        toPerfil.putExtra("PUNTOS_IN", userSended.getPuntosIN());
        toPerfil.putExtra("MONEY", userSended.getUserDinero());
        toPerfil.putExtra("NUM_ED", userSended.getNumExamDiagnostic());
        toPerfil.putExtra("NUM_MD", userSended.getNumMeteoritosDestruidos());
        toPerfil.putExtra("NUM_LC", userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_MT", userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_CS", userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_CN", userSended.getUserImageProfile());
        toPerfil.putExtra("NUM_IN", userSended.getUserImageProfile());


        startActivity(toPerfil);


        return toPerfil;
    }

    public Intent toAbout() {
        Intent toAbout = new Intent(MainMenu.this, About.class);
        return toAbout;
    }

    public Intent toAchievements() {
        Intent toAchievements = new Intent(MainMenu.this, Achievements.class);
        return toAchievements;
    }

    public Intent toShare() {
        Intent toShare = new Intent(MainMenu.this, Share.class);
        return toShare;
    }

    private void goLoginScreen() {
        final Intent intent = new Intent(this, Login.class);
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                } else {
                    Toast.makeText(getApplicationContext(), "Error al cerrar sesion Google", Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    public Intent toSettings() {
        Intent toSettings = new Intent(MainMenu.this, Settings.class);
        return toSettings;
    }

    private void exitApp() {
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLoginScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al cerrar sesion Google", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public void revoke(View view) {
        FirebaseAuth.getInstance().signOut();
        Auth.GoogleSignInApi.revokeAccess(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                if (status.isSuccess()) {
                    goLoginScreen();
                } else {
                    Toast.makeText(getApplicationContext(), "Error al revocar sesion Google", Toast.LENGTH_LONG).show();
                }
            }
        });
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("UserSaved", new Gson().toJson(user));
        Log.e(TAG, "On savedInstance");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.e(TAG, "On restore");
        String dataReceived = savedInstanceState.getString("UserSaved");
        //user = new Gson().fromJson(dataReceived, User.class);

    }


    @Override
    protected void onRestart() {
        Log.e(TAG, "On restart");
        super.onRestart();

    }
}

