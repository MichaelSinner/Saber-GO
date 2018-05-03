package com.uan.michaelsinner.sabergo.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uan.michaelsinner.sabergo.Data.Achievments;
import com.uan.michaelsinner.sabergo.R;

public class Achievements extends AppCompatActivity {

    private Achievments data;
    private Toolbar toolbar;
    private ImageButton log01, log02, log03, log04, log05, log06, log07, log08;
    private TextView tvLog01, tvLog02, tvLog03 , tvLog04, tvLog05, tvLog06, tvLog07, tvLog08;
    private Dialog dialog;
    TextView tvExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Logros");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Sanlabello.ttf");

        dialog = new Dialog(this);

        log01 = (ImageButton) findViewById(R.id.imglog01);
        log02 = (ImageButton) findViewById(R.id.imglog02);
        log03 = (ImageButton) findViewById(R.id.imglog03);
        log04 = (ImageButton) findViewById(R.id.imglog04);
        log05 = (ImageButton) findViewById(R.id.imglog05);
        log06 = (ImageButton) findViewById(R.id.imglog06);
        log07 = (ImageButton) findViewById(R.id.imglog07);
        log08 = (ImageButton) findViewById(R.id.imglog08);

        tvLog01 = (TextView) findViewById(R.id.tvLog01);
        tvLog01.setTypeface(font);
        tvLog02 = (TextView) findViewById(R.id.tvLog02);
        tvLog02.setTypeface(font);
        tvLog03 = (TextView) findViewById(R.id.tvLog03);
        tvLog03.setTypeface(font);
        tvLog04 = (TextView) findViewById(R.id.tvLog04);
        tvLog04.setTypeface(font);
        tvLog05 = (TextView) findViewById(R.id.tvLog05);
        tvLog05.setTypeface(font);
        tvLog06 = (TextView) findViewById(R.id.tvLog06);
        tvLog06.setTypeface(font);
        tvLog07 = (TextView) findViewById(R.id.tvLog07);
        tvLog07.setTypeface(font);
        tvLog08 = (TextView) findViewById(R.id.tvLog08);
        tvLog08.setTypeface(font);


        log01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(1);
            }
        });

        log02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(2);
            }
        });

        log03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(3);
            }
        });

        log04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(4);
            }
        });

        log05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(5);
            }
        });

        log06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(6);
            }
        });

        log07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(7);
            }
        });

        log08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop(8);
            }
        });

    }

    public void showPop(int sel){
        int option = 0;
        option = sel;
        switch (option){
            case 1:

                dialog.setContentView(R.layout.pop_log01);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

            case 2:

                dialog.setContentView(R.layout.pop_log02);
                TextView tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;

            case 3:

                dialog.setContentView(R.layout.pop_log03);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case 4:
                dialog.setContentView(R.layout.pop_log04);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case 5:
                dialog.setContentView(R.layout.pop_log05);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case 6:
                dialog.setContentView(R.layout.pop_log06);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case 7:
                dialog.setContentView(R.layout.pop_log07);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case 8:
                dialog.setContentView(R.layout.pop_log08);
                tvExit = (TextView) dialog.findViewById(R.id.tvClosePop);

                tvExit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;


            default: break;

        }


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
