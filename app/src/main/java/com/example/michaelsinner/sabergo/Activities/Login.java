package com.example.michaelsinner.sabergo.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.michaelsinner.sabergo.R;
import com.example.michaelsinner.sabergo.Utilities.IntentUtil;
import com.example.michaelsinner.sabergo.Utilities.PrefUtil;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;


import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class Login extends AppCompatActivity {


    private LoginButton btnFBLogin;
    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        btnFBLogin = (LoginButton) findViewById(R.id.button_fbLogin);
        ActionBar actionBar = getActionBar();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        btnFBLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                toMenuPrincipal();
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



    }

    public void toMenuPrincipal()
    {
        Intent toMenuPrincial = new Intent(this , MainMenu.class);
        toMenuPrincial.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(toMenuPrincial);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }


}
