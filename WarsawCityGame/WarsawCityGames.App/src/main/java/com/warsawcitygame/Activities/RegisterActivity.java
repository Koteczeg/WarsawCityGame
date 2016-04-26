package com.warsawcitygame.Activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygame.Utils.ValidationUtils;
import com.warsawcitygames.models.RegisterAccountModel;
import com.warsawcitygamescommunication.Services.AccountService;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;


public class RegisterActivity extends AppCompatActivity
{
    @Bind(R.id.register_button) Button registerButton;
    @Bind(R.id.back_button) Button backButton;
    @Bind(R.id.login) EditText loginEditText;
    @Bind(R.id.password) EditText passwordEditText;
    @Bind(R.id.email) EditText emailEditText;
    @Bind(R.id.registerElementsLayout) View registerElementsLayout;

    @Inject AccountService service;
    @Inject SharedPreferences preferences;

    private Dialog dialog;
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        this.context = this.getApplicationContext();
        ((MyApplication) getApplication()).getServicesComponent().inject(this);
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

        registerButton.startAnimation(com.warsawcitygame.Utils.AnimationUtils.getFadeOutAnimation(this));
        registerButton.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (!validateUserRegisterCredentials())
                    return;
                dialog = DialogUtils.RaiseDialogLoading(registerButton.getContext());
                dialog.show();
                RegisterAccountModel model = new RegisterAccountModel(loginEditText.getText().toString(), emailEditText.getText().toString(), passwordEditText.getText().toString());
                Call<ResponseBody> call = service.registerAccount(model);
                call.enqueue(new CustomCallback<ResponseBody>(RegisterActivity.this)
                {
                    @Override
                    public void onSuccess(ResponseBody model)
                    {
                        Toast.makeText(RegisterActivity.this, R.string.success_account_registered, Toast.LENGTH_LONG).show();
                        redirectToLogin();
                    }

                    @Override
                    public void onResponse(Response<ResponseBody> response, Retrofit retrofit)
                    {
                        if (response.code() == 400)
                        {
                            try
                            {
                                handleBadRequest(response);
                            } catch (IOException e)
                            {
                                e.printStackTrace();
                            }
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
        }, com.warsawcitygame.Utils.AnimationUtils.buttonFadeOutAnimationDelay);
    }


    private void handleBadRequest(Response<ResponseBody> response) throws IOException
    {
        dialog.dismiss();
        dialog.dismiss();
        dialog = DialogUtils.RaiseDialogShowError(registerButton.getContext(), "An error occured",extractErrorMessage(response));
        dialog.show();
//        try {
//            new AlertDialog.Builder(RegisterActivity.this)
//                    .setTitle(getString(R.string.error))
//                    .setMessage(extractErrorMessage(response))
//                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                        }
//                    })
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .show();
//        } catch (IOException e) {
//            Log.e("Register", e.getMessage());
//        }
    }

    private String extractErrorMessage(Response<ResponseBody> response) throws IOException {
        String json = response.errorBody().string();
        Gson gson = new Gson();
        ErrorResponse errorResponse = gson.fromJson(json, ErrorResponse.class);
        String errorMessage = errorResponse.getMessage();
        return errorMessage;
    }

    public class ErrorResponse {
        private String message;
        private ModelState modelState;

        public String getMessage() {
            return message;
        }

        public String[] getErrorsMessages() {
            return modelState.getErrors();
        }

        private class ModelState
        {
            @SerializedName("")
            private String[] errors;

            public String[] getErrors() {
                return errors;
            }
        }
    }

    private void redirectToLogin()
    {
        Intent intent = new Intent(context, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private boolean validateUserRegisterCredentials()
    {
        //TODO lepsza walidacja
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
