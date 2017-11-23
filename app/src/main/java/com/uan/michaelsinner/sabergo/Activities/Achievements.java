package com.uan.michaelsinner.sabergo.Activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.uan.michaelsinner.sabergo.Data.Achievments;
import com.uan.michaelsinner.sabergo.R;

public class Achievements extends AppCompatActivity {

    private Achievments data;
    private Toolbar toolbar;
    private ImageButton log01, log02, log03, log04, log05, log06, log07, log08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Logros");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        log01 = (ImageButton) findViewById(R.id.imglog01);
        log02 = (ImageButton) findViewById(R.id.imglog02);
        log03 = (ImageButton) findViewById(R.id.imglog03);
        log04 = (ImageButton) findViewById(R.id.imglog04);
        log05 = (ImageButton) findViewById(R.id.imglog05);
        log06 = (ImageButton) findViewById(R.id.imglog06);
        log07 = (ImageButton) findViewById(R.id.imglog07);
        log08 = (ImageButton) findViewById(R.id.imglog08);



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
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
