package com.warsawcitygame.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.warsawcitygame.R;
import com.warsawcitygame.Utils.AnimationUtils;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.AccessTokenModel;
import com.warsawcitygamescommunication.Services.AccountService;

import javax.inject.Inject;

import butterknife.OnClick;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;


public class LoginActivity extends AppCompatActivity
{
    @Bind(R.id.login_button) Button loginButton;
    @Bind(R.id.register_button) Button registerButton;
    @Bind(R.id.login) TextView loginTextView;
    @Bind(R.id.password) TextView passwordTextView;
    @Bind(R.id.loginElementsLayout) View loginElementsLayout;

    @Inject AccountService service;
    @Inject SharedPreferences preferences;

    private ProgressDialog progressDialog;

    private static final String GRANT_TYPE = "password";
    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String USERNAME_KEY = "username";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ((MyApplication) getApplication()).getServicesComponent().inject(this);
        ButterKnife.bind(this);
        fadeOutLayout();
    }

    private void getToken(final String username, String password) {
        Call<AccessTokenModel> call = service.getToken(username, password, GRANT_TYPE);
        call.enqueue(new CustomCallback<AccessTokenModel>(this)
        {
            @Override
            public void onSuccess(AccessTokenModel model)
            {
                String token = model.getAccessToken();
                if (!TextUtils.isEmpty(token))
                {
                    SharedPreferences.Editor edit = preferences.edit();
                    edit.putString(ACCESS_TOKEN_KEY, token);
                    edit.putString(USERNAME_KEY, username);
                    edit.apply();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onResponse(Response<AccessTokenModel> response, Retrofit retrofit)
            {
                if (!response.isSuccess() && response.code() == 400)
                {
                    progressDialog.dismiss();
                    showIncorrectCredentialsDialog();
                } else
                {
                    super.onResponse(response, retrofit);
                }
            }

            @Override
            public void always()
            {
                progressDialog.dismiss();
            }
        });
    }

    private void showIncorrectCredentialsDialog() {
        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Incorrect credentials")
                .setMessage("Make sure your credentials are correct and try again. Login failed.")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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
        final String login = loginTextView.getText().toString();
        final String password = passwordTextView.getText().toString();
        if (!validateUserCredentials())
        {
            showIncorrectCredentialsDialog();
            return;
        }
        registerButton.setActivated(false);
        loginButton.startAnimation(AnimationUtils.getFadeOutAnimation(this));
        loginButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                progressDialog = ProgressDialog.show(loginButton.getContext(), "Please wait...",
                        "Authenticating in progress.", true);

                getToken(login, password);
            }
        }, AnimationUtils.buttonFadeOutAnimationDelay);
    }

    private boolean validateUserCredentials()
    {
        String login = loginTextView.getText().toString();
        String password = passwordTextView.getText().toString();

        return !(login.isEmpty() || password.isEmpty());
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