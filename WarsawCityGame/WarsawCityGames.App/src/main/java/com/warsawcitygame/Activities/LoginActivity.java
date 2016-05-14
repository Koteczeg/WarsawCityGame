package com.warsawcitygame.Activities;

import android.app.Dialog;
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
import com.warsawcitygame.Utils.DialogUtils;
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

    private Dialog dialog;
    private static final String GRANT_TYPE = "password";
    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String USERNAME_KEY = "username";
    public static final String USER_LOGGED_IN_KEY = "userLoggedIn";


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
                    edit.putBoolean(USER_LOGGED_IN_KEY, true);
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
                    dialog.dismiss();
                    showIncorrectCredentialsDialog();
                } else
                {
                    super.onResponse(response, retrofit);
                }
            }

            @Override
            public void always()
            {
                dialog.dismiss();
            }
        });
    }

    private void showIncorrectCredentialsDialog() {
        dialog = DialogUtils.RaiseDialogShowError(registerButton.getContext(), "An error occured","Make sure your credentials are correct and try again. Login failed.");
        dialog.show();
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
                dialog = DialogUtils.RaiseDialogLoading(loginButton.getContext());
                dialog.show();

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