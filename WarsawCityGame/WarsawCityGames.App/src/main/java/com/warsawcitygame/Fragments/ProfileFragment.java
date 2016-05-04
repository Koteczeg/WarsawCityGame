package com.warsawcitygame.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.okhttp.ResponseBody;
import com.warsawcitygame.Activities.LoginActivity;
import com.warsawcitygame.Activities.MainActivity;
import com.warsawcitygame.R;
import com.warsawcitygame.Utils.CustomCallback;
import com.warsawcitygame.Utils.DelegateAction;
import com.warsawcitygame.Utils.DelegateActionParams;
import com.warsawcitygame.Utils.DialogUtils;
import com.warsawcitygame.Utils.MyApplication;
import com.warsawcitygames.models.AccessTokenModel;
import com.warsawcitygames.models.PlayerProfileDataModel;
import com.warsawcitygamescommunication.Services.AccountService;
import com.warsawcitygamescommunication.Services.UserProfileService;

import javax.inject.Inject;

import butterknife.ButterKnife;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileFragment extends Fragment
{
    private TextView userDescriptionEditable;
    private TextView userLoginEditable;
    private TextView userPasswordEditable;
    private TextView userEmailEditable;

    @Inject UserProfileService service;

    @Inject SharedPreferences preferences;

	public ProfileFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MyApplication) getActivity().getApplication()).getServicesComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeTextViews(rootView);
        setListeners();
        getData();
        return rootView;
    }

    private void setListeners()
    {
        setDialogListener(userDescriptionEditable, "Enter new description", new ChangeDataAction());
        setDialogListener(userLoginEditable, "Enter new login", new ChangeDataAction());
        setDialogListener(userEmailEditable, "Enter new email", new ChangeDataAction());
        setPasswordDialogListener(userPasswordEditable, new ChangePasswordAction());
    }

    private void setPasswordDialogListener(final TextView textView, DelegateActionParams<String> action){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.RaiseChangePasswordDialog(getActivity(), getActivity(), new String[]{"Podaj stare hasło:", "Podaj nowe hasło"}, textView, new ChangePasswordAction());
            }
        });
    }

    private void setDialogListener(final TextView textView,final String text, final DelegateAction action)
    {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.RaiseDialogEditTextView(getActivity(), getActivity(), ((TextView) v).getText().toString(), text, textView, action);
            }
        });
    }

    private void initializeTextViews(View root)
    {
        userDescriptionEditable=ButterKnife.findById(root,R.id.userDescriptionEditable);
        userLoginEditable=ButterKnife.findById(root,R.id.userNameEditable);
        userPasswordEditable=ButterKnife.findById(root,R.id.userPasswordEditable);
        userEmailEditable=ButterKnife.findById(root,R.id.userEmailEditable);
    }

    private void getData(){
        String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
        Call<PlayerProfileDataModel> call = service.GetProfileData(username);
        call.enqueue(new CustomCallback<PlayerProfileDataModel>(getActivity())
        {
            @Override
            public void onSuccess(PlayerProfileDataModel model) {
            }

            @Override
            public void onResponse(Response<PlayerProfileDataModel> response, Retrofit retrofit){
                DialogUtils.RaiseDialogShowError(getActivity(), "Response", "Code: "+response.code()+" "+ response.message());
            }

            @Override
            public void onFailure(Throwable t) {
                DialogUtils.RaiseDialogShowError(getActivity(), "Error", "Error "+t.getMessage());
                super.onFailure(t);
            }

            @Override
            public void always() {
                DialogUtils.RaiseDialogShowError(getActivity(), "Huj", "Kurwa always");
            }
        });
    }

    private void ChangeData(){
        String username = preferences.getString(LoginActivity.USERNAME_KEY, null);
    }

    private void ChangePassword(String currentPassword, String newPassword){

    }

    class ChangeDataAction implements DelegateAction{
        public void ExecuteAction(){
            ChangeData();
        }
    }

    class ChangePasswordAction implements DelegateActionParams<String> {
        public void ExecuteAction(String[] params){
            ChangePassword(params[0], params[1]);
        }
    }
}
