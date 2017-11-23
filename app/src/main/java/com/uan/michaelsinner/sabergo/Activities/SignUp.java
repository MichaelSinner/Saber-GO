package com.uan.michaelsinner.sabergo.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;
import com.facebook.CallbackManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {



    private Toolbar toolbar;

    private Button btnSignUp, btnVolver;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private CallbackManager callbackManager;
    private ProgressBar progressBarFireBase;

    private Snackbar snackbar;

    private TextView tvSubtitle, tvTitle, tvYear;
    private EditText etEmail, etPassword, etNombre, etClave;

    private DatabaseReference mDatabase;
    private DatabaseReference userReference;
    private Handler handler = new Handler();

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");
        tvTitle = (TextView) findViewById(R.id.tvTituloRegistro);
        tvTitle.setTypeface(font);
        tvSubtitle = (TextView) findViewById(R.id.tvSubtituloRegistro);
        tvSubtitle.setTypeface(font);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        userReference = mDatabase.child("Student");

        etEmail = (EditText) findViewById(R.id.etEmailSignUp);
        etPassword = (EditText) findViewById(R.id.etPasswordSignUp);
        etNombre= (EditText) findViewById(R.id.etNameRegister);


        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strEmail = etEmail.getText().toString();
                String strPassword = etPassword.getText().toString();
                String strName = etNombre.getText().toString();


                if (TextUtils.isEmpty(strEmail) || TextUtils.isEmpty(strPassword)) {
                    etEmail.setError("Escribe tu correo aquí");
                    etPassword.setError("Escribe tu contraseña");
                    snackbar = Snackbar.make(view.getRootView(), " Correo electrónico o contraseña no valido ", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    return;
                }else if(!isEmailValid(strEmail)){
                    snackbar = Snackbar.make(view.getRootView(), " Correo electrónico no valido ", Snackbar.LENGTH_SHORT);

                    snackbar.show();
                    return;
                }else if (isOnline(getApplicationContext())) {

                    toRegister(strEmail, strPassword, strName, view);
                    //etClave.setVisibility(View.VISIBLE);

                    //
                } else {
                    startActivity(toNoInternet());
                }

            }

        });
        btnSignUp.setTypeface(font);

        btnVolver = (Button) findViewById(R.id.btnVolver);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(toLogin());
            }
        });



        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            toMenuPrincipal(etNombre);
                        }
                    }, 3000);
                }
            }
        };


    }

    public static boolean isEmailValid(String email) {
        return !(email == null || TextUtils.isEmpty(email)) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




    private Intent toNoInternet() {
        Intent toNoInternet = new Intent(SignUp.this, NoInternet.class);
        return toNoInternet;
    }

    private Intent toLogin() {
        Intent toLogin = new Intent(SignUp.this, Login.class);
        toLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return toLogin;
    }

    private void toRegister(final String emailEditText, final String passwordEditText, final String etName, final View view) {

        firebaseAuth.createUserWithEmailAndPassword(emailEditText, passwordEditText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public static final String TAG = "1";

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Log.d(TAG, "FAILED");
                    Snackbar snackbar = Snackbar.make(view.getRootView(), " Error al intentar Registrar ", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                    //Toast.makeText(getApplicationContext(), "Error al intentar registrar ", Toast.LENGTH_LONG).show();
                }else{

                    FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
                    final String ID = userF.getUid();
                    user = new User(ID, emailEditText, passwordEditText , etName, "EM", 1, "Recluta", 0, 0, 0, 0, 0, 1000, 0, 0, 0, 0, 0, 0, 0, 0, false,0);
                    userReference.child(ID).setValue(user);

                    Snackbar snackbar = Snackbar.make(view.getRootView(), " El usuario "+etName+" ha sido registrado", Snackbar.LENGTH_SHORT);
                    snackbar.show();

                }
            }
        });

    }

    public void toMenuPrincipal(EditText etEmail) {
        Intent toMenuPrincial = new Intent(this, MainMenu.class);
        toMenuPrincial.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        Snackbar.make(getWindow().getDecorView().getRootView()," Bienvenido "+etEmail.getText().toString(), Snackbar.LENGTH_LONG).show();
        startActivity(toMenuPrincial);
    }


    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(fireAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(fireAuthStateListener);
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
}
