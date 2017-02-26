package com.example.michaelsinner.sabergo.Activities;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.michaelsinner.sabergo.R;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;


public class Index extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        if (AccessToken.getCurrentAccessToken() == null)  goLoginScreen();

    }

    private void goLoginScreen()
    {
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void logOut(View view) {
        LoginManager.getInstance().logOut();
        goLoginScreen();
    }
}
