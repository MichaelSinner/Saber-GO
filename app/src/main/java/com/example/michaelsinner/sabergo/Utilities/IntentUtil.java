package com.example.michaelsinner.sabergo.Utilities;

import android.app.Activity;
import android.content.Intent;

import com.example.michaelsinner.sabergo.Activities.MainMenu;

/**
 * Created by Michael Sinner on 21/2/2017.
 */

public class IntentUtil
{
    private Activity activity;

    public IntentUtil(Activity activity)
    {
        this.activity = activity;
    }

    public void showAccesToken()
    {
        Intent i = new Intent(activity, MainMenu.class);
        activity.startActivity(i);
    }

}
