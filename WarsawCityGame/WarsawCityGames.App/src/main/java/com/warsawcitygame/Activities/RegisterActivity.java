package com.warsawcitygame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.ValidationUtils;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;


public class RegisterActivity extends AppCompatActivity
{
    @Bind(R.id.register_button) Button registerButton;
    @Bind(R.id.back_button) Button backButton;
    @Bind(R.id.login) EditText loginEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.email) EditText emailEditText;
    @Bind(R.id.registerElementsLayout) View registerElementsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        fadeOutLayout();
    }

    private void fadeOutLayout()
    {
        Animation fadeOut = new AlphaAnimation(0f, 1f);
        final int delay= 1000;
        fadeOut.setDuration(delay);
        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation)
            {
            }
            @Override
            public void onAnimationRepeat(Animation animation)
            {
            }
            @Override
            public void onAnimationEnd(Animation animation)
            {
                registerElementsLayout.setVisibility(View.VISIBLE);
            }
        });
        registerElementsLayout.startAnimation(fadeOut);
    }

    @OnClick(R.id.back_button)
    public void back()
    {
        backButton.startAnimation(com.warsawcitygame.Utils.AnimationUtils.getFadeOutAnimation(this));
        backButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                redirectToLogin();
            }
        }, com.warsawcitygame.Utils.AnimationUtils.buttonFadeOutAnimationDelay);
    }

    @OnClick(R.id.register_button)
    public void register()
    {
        if(!validateUserRegisterCredentials())
            return;
        registerButton.startAnimation(com.warsawcitygame.Utils.AnimationUtils.getFadeOutAnimation(this));
        registerButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                DialogUtils.showShortToast("Account created", registerButton.getContext());
                redirectToLogin();
            }
        }, com.warsawcitygame.Utils.AnimationUtils.buttonFadeOutAnimationDelay);
    }

    private void redirectToLogin()
    {
        Intent intent = new Intent(backButton.getContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserRegisterCredentials()
    {
        if(loginEditText.getText().toString().isEmpty())
        {
            DialogUtils.showShortToast("Invalid login", this);
            return false;
        }
        if(passwordEditText.getText().toString().isEmpty())
        {
            DialogUtils.showShortToast("Invalid password", this);
            return false;
        }
        if(!ValidationUtils.isValidEmail(emailEditText.getText().toString()))
        {
            DialogUtils.showShortToast("Invalid email", this);
            return false;
        }
        return true;
    }
}
