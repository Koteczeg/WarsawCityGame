package com.warsawcitygame.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.warsawcitygame.CustomControls.KenBurnsView;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.ValidationUtils;


public class RegisterActivity extends AppCompatActivity {
    private KenBurnsView mKenBurns;
    final int delay = 250;
    private Button registerButton;
    private Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        registerButton = (Button)findViewById(R.id.register_button);
        backButton = (Button)findViewById(R.id.backButton);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        setRegisterButtonFeatures(animAlpha);
        setbackButtonFeatures(animAlpha);
        mKenBurns = (KenBurnsView) findViewById(com.warsawcitygame.R.id.splash_background);
        mKenBurns.setImageResource(com.warsawcitygame.R.drawable.splash_background);
        final View registerElementsLayout = findViewById(R.id.registerElementsLayout);
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
                registerElementsLayout.setVisibility(View.VISIBLE);

            }
        });

        registerElementsLayout.startAnimation(fadeOut);

    }

    private void setbackButtonFeatures(final Animation animAlpha) {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, delay);
            }
        });
    }

    private void setRegisterButtonFeatures(final Animation animAlpha) {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(!validateInput())
                    return;
                v.startAnimation(animAlpha);
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showShortToast("Account created");
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, delay);
            }
        });
    }



    private boolean validateInput() {
        EditText loginTxt = (EditText)findViewById(R.id.login_textview);
        EditText passwordTxt = (EditText)findViewById(R.id.password_textview);
        EditText emailTxt = (EditText)findViewById(R.id.emailTxt);
        if(loginTxt.getText().toString().isEmpty())
        {
            showShortToast("Invalid login");
            return false;
        }
        if(passwordTxt.getText().toString().isEmpty())
        {
            showShortToast("Invalid password");
            return false;
        }
        if(!ValidationUtils.isValidEmail(emailTxt.getText().toString()))
        {
            showShortToast("Invalid email");
            return false;
        }
        return true;
    }


    private void showShortToast(String txt)
    {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, txt, duration);
        toast.show();
    }


}
