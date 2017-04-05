package com.example.michaelsinner.sabergo.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.R;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;



import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;


public class Login extends AppCompatActivity {


    private LoginButton btnFBLogin;
    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;
    private ProgressBar progressBarFireBase;
    private TextView tvprueba;
    private Button btnLogIn;
    private EditText etEmail, etPassword;




    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBarFireBase = (ProgressBar) findViewById(R.id.prgBarFirebase);

        callbackManager = CallbackManager.Factory.create();
        btnFBLogin = (LoginButton) findViewById(R.id.button_fbLogin);
        ActionBar actionBar = getActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        btnFBLogin.setReadPermissions(Arrays.asList("email"));
        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                handleFacebookAccesToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG);
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(Login.this, "Login Cancel", Toast.LENGTH_LONG);
            }
        });

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        tvprueba = (TextView) findViewById(R.id.textViewPrueba);

        btnLogIn = (Button) findViewById(R.id.btnIniciarSesion);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toLogin(etEmail.getText().toString(),etPassword.getText().toString());
                tvprueba.setText(etEmail.getText());
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        fireAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    toMenuPrincipal();

                }
            }
        };

    }


    private void handleFacebookAccesToken(AccessToken accessToken) {
        progressBarFireBase.setVisibility(View.VISIBLE);
        btnFBLogin.setVisibility(View.GONE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Error Firebase",Toast.LENGTH_LONG).show();
                }
                progressBarFireBase.setVisibility(View.GONE);
                btnFBLogin.setVisibility(View.VISIBLE);
            }
        });
    }




    public void toMenuPrincipal()
    {
        Intent toMenuPrincial = new Intent(this , MainMenu.class);
        toMenuPrincial.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMenuPrincial);
    }


    private void toLogin(String emailEditText, String passwordEditText)
    {

        firebaseAuth.signInWithEmailAndPassword(emailEditText,passwordEditText).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            public static final String TAG = "1" ;

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Log.d(TAG, "FAILED");
                    Toast.makeText(getApplicationContext(), "Error email auntenticacion",Toast.LENGTH_LONG);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
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
}
