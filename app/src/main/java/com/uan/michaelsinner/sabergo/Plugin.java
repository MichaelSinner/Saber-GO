package com.uan.michaelsinner.sabergo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.uan.michaelsinner.sabergo.Activities.ModuloLudica;
import com.unity3d.player.UnityPlayerActivity;

/**
 * Created by Michael Sinner on 5/2/2018.
 */

public class Plugin extends UnityPlayerActivity{

    static String TAG = "01";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);



    }

    public static void startActivity(String msg){

        Log.e(TAG," SE ACTIVO EL METODO  ASDJSALDJASLDJASDLJ  :  "+msg);
        //Intent toARModule = new Intent(UnityPlayer.currentActivity.getApplicationContext(), ModuloLudica.class);
        //UnityPlayer.currentActivity.finish();
        Intent intentPrueba;

        intentPrueba = new Intent();
        Uri uri = Uri.parse("http://www.google.com");
        intentPrueba= new Intent(Intent.ACTION_VIEW, uri);


    }
    public static void Launch(Activity activity)
    {

        Intent intent = new Intent(activity.getApplicationContext(), ModuloLudica.class);
        activity.startActivity(intent);
        activity.finish();
    }


}
