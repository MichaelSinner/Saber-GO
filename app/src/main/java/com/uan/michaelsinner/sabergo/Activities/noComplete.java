package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.uan.michaelsinner.sabergo.Data.User;
import com.uan.michaelsinner.sabergo.R;

public class noComplete extends AppCompatActivity {

    private Button btnVolver;
    TextView tvsub01, tvsub02;
    String stringUser, idUser;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.actionbar_home, null);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(com.uan.michaelsinner.sabergo.R.layout.activity_no_complete);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");

        tvsub01 =(TextView) findViewById(R.id.tvSub01);
        tvsub01.setTypeface(font);
        tvsub02 =(TextView) findViewById(R.id.tvSub02);
        tvsub02.setTypeface(font);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        stringUser = bundle.getString("USER");
        currentUser = new Gson().fromJson(stringUser, User.class);



        btnVolver = (Button) findViewById(R.id.btnExitTime);
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMenu();
            }
        });
        btnVolver.setTypeface(font);
    }

    public void goToMenu(){
        Intent toResults = new Intent(noComplete.this, MainMenu.class);
        toResults.putExtra("USER", new Gson().toJson(currentUser));
        toResults.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        startActivity(toResults);
        this.finish();

    }
}
