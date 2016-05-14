package com.warsawcitygame.Activities;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.MyApplication;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends Activity
{
    public static final String USER_LOGGED_IN_KEY = "userLoggedIn";
    @Inject SharedPreferences preferences;


    @Bind(R.id.splashElementsLayout) View splashElementsLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ((MyApplication) getApplication()).getServicesComponent().inject(this);
        ButterKnife.bind(this);
        setAnimation();
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Animation fadeOut = setElementsLayoutAnimation();
        setPostDelayLogoAnimation(fadeOut);

    }

    private void setPostDelayLogoAnimation(Animation fadeOut)
    {
        splashElementsLayout.startAnimation(fadeOut);
        int SPLASH_TIME_OUT = 5000;
        new Handler().postDelayed(new Runnable()
        {

            @Override
            public void run()
            {
                Intent i;
                if (preferences.contains(USER_LOGGED_IN_KEY) && preferences.getBoolean(USER_LOGGED_IN_KEY, true))
                {
                    i = new Intent(getApplicationContext(), MainActivity.class);
                } else
                {
                    i = new Intent(getApplicationContext(), LoginActivity.class);
                }
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @NonNull
    private Animation setElementsLayoutAnimation()
    {
        Animation fadeOut = new AlphaAnimation(0f, 1f);
        fadeOut.setDuration(1000);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                splashElementsLayout.setVisibility(View.VISIBLE);

            }
        });
        return fadeOut;
    }

    private void setAnimation()
    {
        ObjectAnimator scaleXAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleX", 5.0F, 1.0F);
        scaleXAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleXAnimation.setDuration(1200);
        ObjectAnimator scaleYAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "scaleY", 5.0F, 1.0F);
        scaleYAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleYAnimation.setDuration(1200);
        ObjectAnimator alphaAnimation = ObjectAnimator.ofFloat(findViewById(R.id.welcome_text), "alpha", 0.0F, 1.0F);
        alphaAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        alphaAnimation.setDuration(1200);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(scaleXAnimation).with(scaleYAnimation).with(alphaAnimation);
        animatorSet.setStartDelay(500);
        animatorSet.start();
        findViewById(R.id.imagelogo).setAlpha(1.0F);
        Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.translate_top_to_center);
        findViewById(R.id.imagelogo).startAnimation(anim);
    }
}