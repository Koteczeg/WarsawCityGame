package com.warsawcitygame.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.warsawcitygame.CustomControls.KenBurnsView;
import com.warsawcitygame.R;


public class LoginActivity extends AppCompatActivity {

    final int delay = 500;
    private KenBurnsView mKenBurns;
    private Button loginButton;
    private Button registerButton;
    private TextView loginTxt;
    private TextView passwordTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.anim_alpha);

        mKenBurns = (KenBurnsView) findViewById(com.warsawcitygame.R.id.ken_burns_images);
        mKenBurns.setImageResource(com.warsawcitygame.R.drawable.splash_background);
        loginTxt = (TextView)findViewById(R.id.loginTxt);
        passwordTxt = (TextView)findViewById(R.id.passwordTxt);
        joinButtons();
        setLoginButtonFeatures(animAlpha);
        setRegisterButtonFeatures(animAlpha);
    }

    private void joinButtons() {
        loginButton = (Button)findViewById(R.id.loginButton);
        registerButton = (Button)findViewById(R.id.registerButton);
    }

    private void setRegisterButtonFeatures(final Animation animAlpha) {
        loginButton.setActivated(false);
        registerButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(v.getContext(), RegisterActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, delay);
            }
        });
    }

    private void setLoginButtonFeatures(final Animation animAlpha) {
        registerButton.setActivated(false);
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View v)
            {
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if(!validateInput())
                        {
                            showShortToast("Login failed");
                            return;
                        }
                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, delay);
            }
        });
    }

    private boolean validateInput()
    {
        String a = loginTxt.getText().toString();
        String b = passwordTxt.getText().toString();


        if(a.equals("a") && b.equals("b"))
        {
            showShortToast("Login succeed");
            return true;
        }
        return false;
    }
    private void showShortToast(String txt)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, txt, duration);
        toast.show();
    }
}