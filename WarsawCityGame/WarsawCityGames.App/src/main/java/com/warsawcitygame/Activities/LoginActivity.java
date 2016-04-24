package com.warsawcitygame.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.AnimationUtils;
import com.warsawcitygame.Utils.DialogUtils;

import butterknife.OnClick;
import butterknife.Bind;
import butterknife.ButterKnife;


public class LoginActivity extends AppCompatActivity
{
    @Bind(R.id.login_button) Button loginButton;
    @Bind(R.id.register_button) Button registerButton;
    @Bind(R.id.login_textview) TextView loginTextView;
    @Bind(R.id.password_textview) TextView passwordTextView;
    @Bind(R.id.loginElementsLayout) View loginElementsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        fadeOutLayout();
    }

    @OnClick(R.id.register_button)
    public void register()
    {
        loginButton.setActivated(false);
        registerButton.startAnimation(AnimationUtils.getFadeOutAnimation(this));
        registerButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                Intent intent = new Intent(registerButton.getContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        }, AnimationUtils.buttonFadeOutAnimationDelay);
    }

    @OnClick(R.id.login_button)
    public void login()
    {
        registerButton.setActivated(false);
        loginButton.startAnimation(AnimationUtils.getFadeOutAnimation(this));
        loginButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {

                if (!validateUserCredentials())
                {
                    DialogUtils.showShortToast(getString(R.string.login_failed), loginButton.getContext());
                    return;
                }

                Intent intent = new Intent(loginButton.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, AnimationUtils.buttonFadeOutAnimationDelay);
    }

    private boolean validateUserCredentials()
    {
        String login = loginTextView.getText().toString();
        String password = passwordTextView.getText().toString();
        return validateUserCredentialsAsync(login, password);
    }

    //TODO async communication layer
    private boolean validateUserCredentialsAsync(String login, String password)
    {
        if(login.equals("a") && password.equals("b"))
        {
            DialogUtils.showShortToast("Login succeed", this);
            return true;
        }
        return false;
    }

    private void fadeOutLayout()
    {
        Animation fadeOut = new AlphaAnimation(0f, 1f);
        final int delay = 1000;
        fadeOut.setDuration(delay);
        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation)
            {
                loginElementsLayout.setVisibility(View.VISIBLE);
            }
        });
        loginElementsLayout.startAnimation(fadeOut);
    }
}