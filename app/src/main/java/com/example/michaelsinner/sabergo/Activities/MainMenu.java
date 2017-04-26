package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;


import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.michaelsinner.sabergo.R;
import com.example.michaelsinner.sabergo.Utilities.PrefUtil;
import com.facebook.*;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;


public class MainMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "01" ;
    private Button btnPruebaDiagn;
    private Button btnPruebaDiaria;
    private Button btnSimulacro;

    private String userName, userEmail;

    private ImageView ivUserImagProfile;
    TextView tvUserName, tvUserEmail, tcUserLevel, prueba;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Menú Principal");

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View viewNav = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        prueba = (TextView) findViewById(R.id.tvPruebaMenu);
        tvUserName = (TextView) viewNav.findViewById(R.id.tvUserNameMain);
        tvUserEmail = (TextView) viewNav.findViewById(R.id.tvUserEmailMain);
        ivUserImagProfile = (ImageView) viewNav.findViewById(R.id.imgProfile);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle!=null){
            String email = (String) bundle.get("EMAIL");
            tvUserEmail.setText(email);
            Bitmap imageProfile = (Bitmap) bundle.get("IMAGE");
            ivUserImagProfile.setImageBitmap(imageProfile);
        }


        if(AccessToken.getCurrentAccessToken()== null)
        {
            goLoginScreen();
        }else{
            com.facebook.Profile perfilFb = Profile.getCurrentProfile();
          //  tvUsername.setText("Holo");
          //  tvUserEmail.setText(perfilFb.getFirstName());
            prueba.setText(perfilFb.getFirstName()+" "+perfilFb.getLastName());
            tvUserName.setText(perfilFb.getFirstName()+" "+perfilFb.getLastName());
            String userI = perfilFb.getId();

        }

//                    toMenuPrincipal();

        btnPruebaDiagn = (Button) findViewById(R.id.btnPruebaDiagnostico);
        btnPruebaDiagn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(toPruebaDiagnostico());
            }
        });

        btnPruebaDiaria = (Button) findViewById(R.id.btnPruebasDiarias);
        btnPruebaDiaria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toPruebaEntrenamiento());
            }
        });

        btnSimulacro = (Button) findViewById(R.id.btnMeteoros);
        btnSimulacro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(toModuloLudica());
            }
        });

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

        if (id == R.id.itmPruebaDiagnostico) {
            startActivity(toPruebaDiagnostico());
        } else if (id == R.id.itmPruebaDiaria) {
            startActivity(toPruebaEntrenamiento());
        } else if (id == R.id.itmLudica) {
            startActivity(toModuloLudica());
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
        }else if (id == R.id.nav_share){
            startActivity(toShare());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Intent toPruebaDiagnostico()
    {
        Intent toPruebaDiagnostico = new Intent(MainMenu.this, PruebaDiagnostico.class);
        return  toPruebaDiagnostico;
    }
    private Intent toPruebaEntrenamiento(){
        Intent toPruebaEntrenamiento = new Intent(MainMenu.this, PruebaDiagnostico.class);
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
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    public Intent toSettings(){
        Intent toSettings = new Intent(MainMenu.this, Settings.class);
        return  toSettings;
    }
    private void exitApp(){
        FirebaseAuth.getInstance().signOut();
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }


}
