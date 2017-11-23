package com.uan.michaelsinner.sabergo.Utilities;

import com.uan.michaelsinner.sabergo.R;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

/**
 * Created by Michael Sinner on 16/11/2017.
 */

public class TextFade {
    Context context;
    private TextView texto = null;
    private Animation fadeIn = null;
    private Animation fadeOut = null;
    // Listeners que detectan el fin de la animación
    private LocalFadeInAnimationListener myFadeInAnimationListener = new LocalFadeInAnimationListener();
    private LocalFadeOutAnimationListener myFadeOutAnimationListener = new LocalFadeOutAnimationListener();


    public TextFade(Context context, TextView text){
        this.context = context;
        this.texto = (TextView)text;
        runAnimations();
    }

    private void launchOutAnimation() {
        texto.startAnimation(fadeOut);
    }

    /**
     * Performs the actual fade-in
     */
    private void launchInAnimation() {
        texto.startAnimation(fadeIn);
    }

    /**
     * Comienzo de la animación
     */
    private void runAnimations() {
        //uso de las animaciones
        fadeIn = AnimationUtils.loadAnimation(this.context, R.anim.text_fadein);
        fadeIn.setAnimationListener( myFadeInAnimationListener );
        fadeOut = AnimationUtils.loadAnimation(this.context, R.anim.text_fadeout);
        fadeOut.setAnimationListener( myFadeOutAnimationListener );
        // And start
        launchInAnimation();
    }

    // Runnable que arranca la animación
    private Runnable mLaunchFadeOutAnimation = new Runnable() {
        public void run() {
            launchOutAnimation();
        }
    };

    private Runnable mLaunchFadeInAnimation = new Runnable() {
        public void run() {
            launchInAnimation();
        }
    };

    /**
     * Listener para la animacion del Fadeout
     *
     * @author moi
     *
     */
    private class LocalFadeInAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            texto.post(mLaunchFadeOutAnimation);
        }
        public void onAnimationRepeat(Animation animation){
        }
        public void onAnimationStart(Animation animation) {
        }
    };

    /**
     * Listener de animación para el Fadein
     */
    private class LocalFadeOutAnimationListener implements Animation.AnimationListener {
        public void onAnimationEnd(Animation animation) {
            texto.post(mLaunchFadeInAnimation);
        }
        public void onAnimationRepeat(Animation animation) {
        }
        public void onAnimationStart(Animation animation) {
        }
    };
}
