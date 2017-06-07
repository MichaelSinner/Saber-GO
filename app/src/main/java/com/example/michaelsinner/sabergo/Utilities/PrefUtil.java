package com.example.michaelsinner.sabergo.Utilities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Michael Sinner on 21/2/2017.
 */

public class PrefUtil
{
    private Activity activity;

    public PrefUtil(Activity activity)
    {
        this.activity = activity;
    }

    public void saveAccesToken(String Token)
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("token", Token);
        editor.apply();
    }

    public String getToken()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        return sp.getString("token", null);
    }

    public void clearToken()
    {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(activity);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

}
