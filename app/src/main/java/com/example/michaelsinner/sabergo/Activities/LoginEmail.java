package com.example.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginEmail extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogIn;
    private Button btnSignIn;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener fireAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);



        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btnLogIn = (Button) findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFields()) {
                    signIn();
                }else {
                   Toast toastError =  Toast.makeText(getApplicationContext(),"Error al iniciar sesion", Toast.LENGTH_SHORT);
                    toastError.show();
                }

            }
        });
        btnSignIn = (Button) findViewById(R.id.btnSingIn);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    private boolean checkFields() {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
    private void signIn()
    {
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                toMenuPrincipal();


                }else{
                    Toast.makeText(LoginEmail.this, "Sign In Failed",Toast.LENGTH_LONG);
                                    }
            }
        });
    }

    public void toMenuPrincipal()
    {
        Intent intent = new Intent(LoginEmail.this, MainMenu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}

