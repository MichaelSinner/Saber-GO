package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.uan.michaelsinner.sabergo.R;

public class About extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvLicence, tvVersion, tvMichi;
    private Button btnStarr, btnPage, btnQr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Acerca de");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");

        tvLicence = (TextView) findViewById(R.id.tv_licence);
        tvLicence.setTypeface(font);
        tvVersion = (TextView) findViewById(R.id.tv_version);
        tvVersion.setTypeface(font);
        tvMichi = (TextView) findViewById(R.id.tv_michi);
        tvMichi.setTypeface(font);
        btnStarr = (Button) findViewById(R.id.btnStart);
        btnStarr.setTypeface(font);
        btnPage = (Button) findViewById(R.id.btnPageMain);
        btnPage.setTypeface(font);
        btnQr = (Button) findViewById(R.id.btnPageMarc);
        btnQr.setTypeface(font);

        btnPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://saber-go.firebaseapp.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btnQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://saber-go.firebaseapp.com/res/QR.png");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       if (id == android.R.id.home) {
            Intent intent = NavUtils.getParentActivityIntent(this);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            NavUtils.navigateUpTo(this, intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
